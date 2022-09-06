package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.item.SkuDetailFeignClient;
import com.atguigu.gmall.feign.product.SkuProductFeignClient;
import com.atguigu.gmall.feign.search.SearchFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.starter.cache.annotation.GmallCache;
import com.atguigu.starter.cache.service.CacheOpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @Author : dyh
 * @Date: 2022/8/26
 * @Description : com.atguigu.gmall.item.service.impl
 * @Version : 1.0
 */
@Service
@Slf4j
public class SkuDetailServiceImpl implements SkuDetailService {

    @Autowired
    SkuProductFeignClient skuDetailFeignClient;

    private Map<Long,SkuDetailTo> skuCache = new ConcurrentHashMap<>();
    /**
     * 可配置的线程池，可自动注入
     */
    @Autowired
    ThreadPoolExecutor executor;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    CacheOpsService cacheOpsService;
    @Autowired
    SearchFeignClient searchFeignClient;

    /**
     * params 代表方法的所有参数列表
     * @param skuId
     * @return
     */
    @Transactional
    @GmallCache(
            cacheKey =SysRedisConst.SKU_INFO_PREFIX+"#{#params[0]}",
            bloomName = SysRedisConst.BLOOM_SKUID,
            bloomValue = "#{#params[0]}",
            lockName = SysRedisConst.LOCK_SKU_DETAIL+"#{#params[0]}"
    )
    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
        SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
        return fromRpc;
    }

    @Override
    public void updateHotScore(Long skuId) {
        //redis统计得分
        Long increment = redisTemplate.opsForValue().increment(SysRedisConst.SKU_HOTSCORE_PREFIX + skuId);
        if(increment % 100 == 0){
            //定量更新es
            searchFeignClient.updateHotScore(skuId,increment);
        }
    }

    //未缓存优化前
    public SkuDetailTo getSkuDetailFromRpc(Long skuId) {
        SkuDetailTo detailTo = new SkuDetailTo();
        /**
         * 以前   一次远程超级调用【网络交互比较浪费时间】  查出所有数据直接给我们返回
         *  一次远程用 2s
         * CountDownLatch downLatch = new CountDownLatch(6);
         *  6次远程调用 6s + 0.5*6 = 9s
         * 同步调用
         * 远程调用其实不用等待，各查各的。 异步的方式
         */
        /**
         * CompletableFuture.runAsync()// CompletableFuture<Void>  启动一个下面不用它返回结果的异步任务
         * CompletableFuture.supplyAsync()//CompletableFuture<U>  启动一个下面用它返回结果的异步任务
         */
        //1.查询基本信息
        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            Result<SkuInfo> result = skuDetailFeignClient.getSkuInfo(skuId);
            SkuInfo skuInfo = result.getData();
            detailTo.setSkuInfo(skuInfo);
            return skuInfo;
        }, executor);
        //2.查商品的图片信息
        CompletableFuture<Void> imageFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if(skuInfo!=null){
                Result<List<SkuImage>> skuImage = skuDetailFeignClient.getSkuImage(skuId);
                skuInfo.setSkuImageList(skuImage.getData());
            }
        }, executor);
        //3、查商品实时价格 2s
        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() -> {
            Result<BigDecimal> price = skuDetailFeignClient.getSku1010Price(skuId);
            detailTo.setPrice(price.getData());
        }, executor);
        //4.查销售属性名值
        CompletableFuture<Void> saleAttrFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if(skuInfo!=null){
                Long spuId = skuInfo.getSpuId();
                Result<List<SpuSaleAttr>> saleattrvalues = skuDetailFeignClient.getSkuSaleattrvalues(skuId, spuId);
                detailTo.setSpuSaleAttrList(saleattrvalues.getData());
            }
        }, executor);
        //5.查sku组合
        CompletableFuture<Void> completableFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if(skuInfo!=null){
                Result<String> skuValueJson = skuDetailFeignClient.getSkuValueJson(skuInfo.getSpuId());
                detailTo.setValuesSkuJson(skuValueJson.getData());
            }
        }, executor);
        //6.查分类
        CompletableFuture<Void> categoryFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if(skuInfo!=null){
                Result<CategoryViewTo> categoryVies = skuDetailFeignClient.getCategoryVies(skuInfo.getCategory3Id());
                detailTo.setCategoryView(categoryVies.getData());
            }
        }, executor);
        /**
         * 6个任务都结束后，To才能返回
         * 1、CompletableFuture 异步【编排】
         * 启动一个异步任务有多少种方法
         * 1、new Thread().start()
         * 2、Runnable  new Thread(runnable).start();
         * 3、Callable  带结果  FutureTask
         * 4、线程池
         *      executor.submit(()->{});  executor.execute(()->{});
         * 5、异步编排 CompletableFuture
         *     - CompletableFuture启动异步任务
         *     -
         */
        CompletableFuture.allOf(imageFuture,priceFuture,saleAttrFuture,skuInfoFuture,completableFuture,categoryFuture)
                .join();
        return detailTo;


    }

    public SkuDetailTo getSkuDetailBloomLock(Long skuId) {
        String cacheKey = SysRedisConst.SKU_INFO_PREFIX+skuId;
        //1、先查缓存
        SkuDetailTo cacheData = cacheOpsService.getCacheData(cacheKey,SkuDetailTo.class);
        //2、判断
        if(cacheData==null){
            //3、缓存没有
            //4、先问布隆，是否有这个商品
            boolean contain =  cacheOpsService.bloomContains(skuId);
            if(!contain){
                log.info("存在攻击风险");
                return  null;
            }
            //6、布隆说有，有可能有，就需要回源查数据
            boolean lock = cacheOpsService.tryLock(skuId); //为当前商品加自己的分布式锁。100w的49号查询只会放进一个
            if(lock){
                //7、获取锁成功，查询远程
                log.info("[{}]商品 缓存未命中，布隆说有，准备回源.....",skuId);
                SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
                //8、数据放缓存
                cacheOpsService.saveData(cacheKey,fromRpc);
                //解锁
                cacheOpsService.unlock(skuId);
                return fromRpc;
            }
            //9、没获取到锁
            try {
                Thread.sleep(1000);
                return cacheOpsService.getCacheData(cacheKey,SkuDetailTo.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //4、缓存中有
        return cacheData;
    }



//    @Override
//    public SkuDetailTo getSkuDetailxxFuture(Long skuId) {
//        //看缓存
//        String jsonStr = redisTemplate.opsForValue().get("sku:info:" + skuId);
//        if("x".equals(jsonStr)){
//            //说明以前查过，只不过数据库没有此记录，为了避免再次回源，缓存了一个占位符
//            return null;
//        }
//        if(StringUtils.isEmpty(jsonStr)){
//            //回源
//            SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
//            //放入缓存【查到的对象转为json字符串保存到redis】
//            String cacheJson = "x";
//            if(fromRpc!=null){
//                cacheJson = Jsons.toStr(fromRpc);
//                redisTemplate.opsForValue().set("sku:info:"+skuId,cacheJson,7, TimeUnit.DAYS);
//            }else {
//                redisTemplate.opsForValue().set("sku:info:"+skuId,cacheJson,30, TimeUnit.MINUTES);
//            }
//            return fromRpc;
//        }
//        //3、缓存中有. 把json转成指定的对象
//        SkuDetailTo skuDetailTo = Jsons.toObj(jsonStr, SkuDetailTo.class);
//        return skuDetailTo;
//    }
//    //优化后 本地缓存
//    @Override
//    public SkuDetailTo getSkuDetail(Long skuId) {
//        //本地缓存
//        //先看缓存
//        SkuDetailTo cacheData = skuCache.get(skuId);
//        if(cacheData==null){
//            //缓存中没有
//            SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
//            skuCache.put(skuId,fromRpc);
//            return fromRpc;
//        }
//        //缓存有
//        return cacheData;
//    }
    /**
     *  //远程调用商品服务查询
     *         Result<SkuInfo> result = skuDetailFeignClient.getSkuInfo(skuId);
     *         //1.查询基本信息
     *         SkuInfo skuInfo = result.getData();
     *         detailTo.setSkuInfo(skuInfo);
     *         //2.查商品的图片信息
     *         Result<List<SkuImage>> skuImage = skuDetailFeignClient.getSkuImage(skuId);
     *         skuInfo.setSkuImageList(skuImage.getData());
     *         //3.查商品实时价格
     *         Result<BigDecimal> sku1010Price = skuDetailFeignClient.getSku1010Price(skuId);
     *         detailTo.setPrice(sku1010Price.getData());
     *         //4.查销售属性名值
     *         Result<List<SpuSaleAttr>> skuSaleattrvalues = skuDetailFeignClient.getSkuSaleattrvalues(skuId, skuInfo.getSpuId());
     *         detailTo.setSpuSaleAttrList(skuSaleattrvalues.getData());
     *         //5.查sku组合
     *         Result<String> valueJson = skuDetailFeignClient.getSkuValueJson(skuInfo.getSpuId());
     *         detailTo.setValuesSkuJson(valueJson.getData());
     *         //6.查分类
     *         Result<CategoryViewTo> categoryVies = skuDetailFeignClient.getCategoryVies(skuId);
     *         detailTo.setCategoryView(categoryVies.getData());
     */
}