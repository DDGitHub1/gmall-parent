package com.atguigu.gmall.cart.service.impl;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.feign.product.SkuProductFeignClient;
import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.vo.user.UserAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author : dyh
 * @Date: 2022/9/8
 * @Description : com.atguigu.gmall.cart.service.impl
 * @Version : 1.0
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    SkuProductFeignClient skuFeignClient;
    @Autowired
    ThreadPoolExecutor executor;
    @Override
    public SkuInfo addToCart(Long skuId, Integer num) {
        //1. cart:user: == hash(skuId,skuInfo)
        //1.决定购物车使用使用那个键
        String cartKey = determinCarKey();
        //2.给购物车添加指定商品
        SkuInfo skuInfo = addItemToCart(skuId,num,cartKey);
        //3.购物车超时设置，自动延期
        UserAuthInfo authInfo = AuthUtils.getCurrentAuthInfo();
        if (authInfo.getUserId() == null) {
            //用户未登录状态一直操作临时购物车
            String tempKey = SysRedisConst.CART_KEY+authInfo.getUserTempId();
            //临时购物车都有过期时间 自动延期
            redisTemplate.expire(tempKey,90, TimeUnit.DAYS);
        }
        return skuInfo;
    }

    /**
     * 给购物车添加指定商品
     * @param skuId
     * @param num
     * @param cartKey
     * @return
     */
    @Override
    public SkuInfo addItemToCart(Long skuId, Integer num, String cartKey) {
        //拿到购物车
        BoundHashOperations<String, Object, Object> cart = redisTemplate.boundHashOps(cartKey);
        Boolean hasKey = cart.hasKey(skuId.toString());
        Long itemsSize = cart.size();
        //1.如果这个skuId之前没有添加过，就新增，还需要远程调用查询当前信息
        if(!hasKey){
            if(itemsSize + 1 >SysRedisConst.CART_ITEMS_LIMIT){
                // 异常机制
                throw new GmallException(ResultCodeEnum.CART_OVERFLOW);
            }
            //1.1 远程获取商品信息
            SkuInfo data = skuFeignClient.getSkuInfo(skuId).getData();
            //1.2 转为购物车中要保存的数据类型
            CartInfo item = converSkuInfo2CartInfo(data);
            item.setSkuNum(num);//设置好数量
            //1.3 给redis保存起来
            cart.put(skuId.toString(), Jsons.toStr(item));
            return data;
        }else {
            //2.如果这个skuId之前有添加过，就修改skuId对应的商品的数量
            //2.1获取实时价格
            BigDecimal price = skuFeignClient.getSku1010Price(skuId).getData();
            //2.2 获取原来信息
            CartInfo cartInfo = getItemFromCart(cartKey,skuId);
            //2.3 更新商品
            cartInfo.setSkuPrice(price);
            cartInfo.setSkuNum(num);
            cartInfo.setUpdateTime(new Date());
            //2.4 同步到redis中
            cart.put(skuId.toString(),Jsons.toStr(cartInfo));
            SkuInfo skuInfo = converSkuInfo2CartInfo(cartInfo);
            return skuInfo;
        }
    }

    /**
     * 把SkuInfo转为CartInfo
     * @param cartInfo
     * @return
     */
    private SkuInfo converSkuInfo2CartInfo(CartInfo cartInfo) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSkuName(cartInfo.getSkuName());
        skuInfo.setSkuDefaultImg(cartInfo.getImgUrl());
        skuInfo.setId(cartInfo.getSkuId());
        return skuInfo;
    }

    @Override
    public CartInfo getItemFromCart(String cartKey, Long skuId){
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(cartKey);
        //1.拿到购物车中指定商品json数据
        String jsonData = ops.get(skuId.toString());
        return Jsons.toObj(jsonData, CartInfo.class);
    }

    @Override
    public List<CartInfo> getCartList(String carKey) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(carKey);
        //流式编程
        List<CartInfo> infos = hashOps.values().stream()
                .map(str -> Jsons.toObj(str, CartInfo.class))
                .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                .collect(Collectors.toList());
        //顺便把购物车中所有商品的价格再次查询一遍进行更新。 异步不保证立即执行。
        //不用等价格更新。 异步情况下拿不到老请求
        //1、老请求
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //异步会导致feign丢失请求
        executor.submit(()->{
            //2.绑定请求到这个线程
            RequestContextHolder.setRequestAttributes(requestAttributes);
            updateCartAllItemsPrice(carKey,infos);
            //3.移除数据
            RequestContextHolder.resetRequestAttributes();
        });
        return infos;
    }



    /**
     * 更新购物车中某个商品的数量
     * @param skuId
     * @param num
     * @param carKey
     */
    @Override
    public void updateItemNum(Long skuId, Integer num, String carKey) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(carKey);
        //拿到商品
        CartInfo item = getItemFromCart(carKey, skuId);
        item.setSkuNum(item.getSkuNum()+num);
        item.setUpdateTime(new Date());
        //保存到购物车
        hashOps.put(skuId.toString(), Jsons.toStr(item));
    }

    /**
     * 更新购物车中商品的勾选状态
     * @param skuId
     * @param status
     * @param carKey
     */
    @Override
    public void updateChecked(Long skuId, Integer status, String carKey) {
        //1.拿到购物车
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(carKey);
        //拿到要修改的商品
        CartInfo item = getItemFromCart(carKey, skuId);
        item.setIsChecked(status);
        item.setUpdateTime(new Date());
        //3.保存
        hashOps.put(skuId.toString(),Jsons.toStr(item));
    }

    /**
     * 删除购物车中所选中的商品
     * @param cartKey
     */
    @Override
    public void deleteChecked(String cartKey) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(cartKey);

        //1、拿到选中的商品，并删除。收集所有选中商品的id
        List<String> ids = getCheckedItems(cartKey).stream()
                .map(cartInfo -> cartInfo.getSkuId().toString())
                .collect(Collectors.toList());
        if (ids != null && ids.size()>0) {
            hashOps.delete(ids.toArray());
        }
    }

    @Override
    public void deleteCartItem(Long skuId, String cartKey) {
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(cartKey);
        hashOps.delete(skuId.toString());
    }

    @Override
    public void mergeUserAndTempCart() {
        UserAuthInfo authInfo = AuthUtils.getCurrentAuthInfo();
        //判断是否需要合并
        if (authInfo.getUserId()!=null && !StringUtils.isEmpty(authInfo.getUserTempId())) {
            //2.可能需要合并
            //3.临时购物车有东西，合并后删除临时购物车
            String tempCartKey = SysRedisConst.CART_KEY + authInfo.getUserTempId();
            //3.1获取临时购物车中所有商品
            List<CartInfo> cartList = getCartList(tempCartKey);
            if (cartList != null && cartList.size()> 0) {
                //临时购物车中有数据，需要合并
                String userCartKey = SysRedisConst.CART_KEY + authInfo.getUserId();
                for (CartInfo cartInfo : cartList) {
                    Long skuId = cartInfo.getSkuId();
                    Integer skuNum = cartInfo.getSkuNum();
                    addItemToCart(skuId,skuNum,userCartKey);
                    //合并了就删除
                    redisTemplate.opsForHash().delete(tempCartKey,skuId.toString());
                }
            }
        }
    }

    /**
     * 更新这个购物车中所有商品的价格
     * @param carKey
     * @param infos 所有的商品
     */
    @Override
    public void updateCartAllItemsPrice(String carKey, List<CartInfo> infos) {
        BoundHashOperations<String, String, String> cartOps = redisTemplate.boundHashOps(carKey);
        System.out.println("更新价格启动"+ Thread.currentThread());
        //200商品 4s
        infos.stream().forEach(cartInfo -> {
            //1.查出最新价格 15m
            Result<BigDecimal> price = skuFeignClient.getSku1010Price(cartInfo.getSkuId());
            //2.设置新价格
            cartInfo.setSkuPrice(price.getData());
            cartInfo.setUpdateTime(new Date());
            //3.更新购物车价格
            cartOps.put(cartInfo.getSkuId().toString(),Jsons.toStr(cartInfo));
        });
    }

    private List<CartInfo> getCheckedItems(String cartKey) {
        List<CartInfo> cartList = getCartList(cartKey);
        List<CartInfo> checkedItems = cartList.stream()
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .collect(Collectors.toList());
        return checkedItems;
    }


    /**
     * 根据用户信息决定用哪个购物键
     * @return
     */
    @Override
    public String determinCarKey() {
        UserAuthInfo info = AuthUtils.getCurrentAuthInfo();
        String carKey = SysRedisConst.CART_KEY;
        if (info.getUserId()!=null) {
            //用户登陆了
            carKey =  carKey+""+info.getUserId();
        }else {
            //用户未登录使用临时id
            carKey = carKey + ""+ info.getUserTempId();
        }
        return carKey;
    }
    /**
     * 把SkuInfo转为CartInfo
     * @param data
     * @return
     */
    private CartInfo converSkuInfo2CartInfo(SkuInfo data) {

        CartInfo cartInfo = new CartInfo();
        cartInfo.setSkuId(data.getId());
        cartInfo.setImgUrl(data.getSkuDefaultImg());
        cartInfo.setSkuName(data.getSkuName());
        cartInfo.setIsChecked(1);
        cartInfo.setCreateTime(new Date());
        cartInfo.setUpdateTime(new Date());
        cartInfo.setSkuPrice(data.getPrice());
        cartInfo.setCartPrice(data.getPrice());


        return cartInfo;
    }

}
