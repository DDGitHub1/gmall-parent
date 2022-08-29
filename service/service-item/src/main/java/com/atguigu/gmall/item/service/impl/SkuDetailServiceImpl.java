package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.feign.SkuDetailFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author : dyh
 * @Date: 2022/8/26
 * @Description : com.atguigu.gmall.item.service.impl
 * @Version : 1.0
 */
@Service
public class SkuDetailServiceImpl implements SkuDetailService {

    @Autowired
    SkuDetailFeignClient skuDetailFeignClient;
    /**
     * 可配置的线程池，可自动注入
     */
    @Autowired
    ThreadPoolExecutor executor;
    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
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
            Result<List<SkuImage>> skuImage = skuDetailFeignClient.getSkuImage(skuId);
            skuInfo.setSkuImageList(skuImage.getData());
        }, executor);
        //3.查商品实时价格
        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() -> {
            Result<BigDecimal> price = skuDetailFeignClient.getSku1010Price(skuId);
            detailTo.setPrice(price.getData());
        }, executor);
        //4.查销售属性名值
        CompletableFuture<Void> saleAttrFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Long spuId = skuInfo.getSpuId();
            Result<List<SpuSaleAttr>> saleattrvalues = skuDetailFeignClient.getSkuSaleattrvalues(skuId, spuId);
            detailTo.setSpuSaleAttrList(saleattrvalues.getData());
        }, executor);
        //5.查sku组合
        CompletableFuture<Void> completableFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<String> skuValueJson = skuDetailFeignClient.getSkuValueJson(skuInfo.getSpuId());
            detailTo.setValuesSkuJson(skuValueJson.getData());
        }, executor);
        //6.查分类
        CompletableFuture<Void> categoryFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<CategoryViewTo> categoryVies = skuDetailFeignClient.getCategoryVies(skuInfo.getCategory3Id());
            detailTo.setCategoryView(categoryVies.getData());
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