package com.atguigu.gmall.feign.cart;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.product.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/8
 * @Description : com.atguigu.gmall.feign.cart
 * @Version : 1.0
 */
@RequestMapping("/api/inner/rpc/cart")
@FeignClient("service-cart")
public interface CartFeignClient {
    /**
     * 将商品添加到购物车
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/addToCart")
    public Result<SkuInfo> addToCart(@RequestParam("skuId") Long skuId,
                                     @RequestParam("num") Integer num);

    /**
     * 删除购物车中所选中的商品
     * @param
     * @return
     */
    @GetMapping("/deleteChecked")
    public Result deleteChecked();

    /**
     * 获取当前购物车中所有选中商品
     * @return
     */
    @GetMapping("/checked/list")
    public Result<List<CartInfo>> getChecked();
}
