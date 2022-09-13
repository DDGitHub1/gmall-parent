package com.atguigu.gmall.cart.service;

import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.product.SkuInfo;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/8
 * @Description : com.atguigu.gmall.cart.service
 * @Version : 1.0
 */
public interface CartService {
    /**
     * 添加一个商品到购物车
     * @param skuId
     * @param num
     * @return
     */
    SkuInfo addToCart(Long skuId, Integer num);
    /**
     * 根据用户信息决定用哪个购物键
     * @return
     */
    String determinCarKey();

    /**
     * 给购物车添加指定商品
     * @param skuId
     * @param num
     * @param cartKey
     * @return
     */
    SkuInfo addItemToCart(Long skuId, Integer num, String cartKey);

    /**
     *从购物车中获取某个商品
     * @param cartKey
     * @param skuId
     * @return
     */
    CartInfo getItemFromCart(String cartKey, Long skuId);

    /**
     * 获取指定购物车中的所有商品。排好序（按照createTime顺序）。
     * @param carKey
     * @return
     */
    List<CartInfo> getCartList(String carKey);

    /**
     * 更新购物车中某个商品的数量
     * @param skuId
     * @param num
     * @param carKey
     */
    void updateItemNum(Long skuId, Integer num, String carKey);

    /**
     * 更新购物车中商品的勾选状态
     * @param skuId
     * @param status
     * @param carKey
     */
    void updateChecked(Long skuId, Integer status, String carKey);

    /**
     * 删除购物车中所选中的商品
     * @param cartKey
     */
    void deleteChecked(String cartKey);

    /**
     *
     * @param skuId
     * @param cartKey
     */
    void deleteCartItem(Long skuId, String cartKey);

    /**
     * 合并购物车
     */
    void mergeUserAndTempCart();

    /**
     * 更新这个购物车中所有商品的价格
     * @param carKey
     * @param infos 所有的商品
     */
     void updateCartAllItemsPrice(String carKey,List<CartInfo> infos);
}
