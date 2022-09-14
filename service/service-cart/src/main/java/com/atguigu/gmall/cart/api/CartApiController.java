package com.atguigu.gmall.cart.api;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.product.SkuInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/8
 * @Description : com.atguigu.gmall.cart.api
 * @Version : 1.0
 */
@Slf4j
@RequestMapping("/api/inner/rpc/cart")
@RestController
public class CartApiController {

    @Autowired
    CartService cartService;
    /**
     * 将商品添加到购物车
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/addToCart")
    public Result<SkuInfo> addToCart(@RequestParam("skuId") Long skuId,
                                     @RequestParam("num") Integer num){
//        UserAuthInfo authInfo = AuthUtils.getCurrentAuthInfo();
//        System.out.println("service-cart 获取到的用户id :" + authInfo.getUserId());
//        log.info("用户id 是 : {}临时id : {}",authInfo.getUserId(),authInfo.getUserTempId());
//        return Result.ok();
        SkuInfo skuInfo = cartService.addToCart(skuId,num);
        return Result.ok(skuInfo);
    }

    //api/cart/deleteCart/53

    /**
     * 删除购物车中所选中的商品
     * @param
     * @return
     */
    @GetMapping("/deleteChecked")
    public Result deleteCart(){
        String cartKey = cartService.determinCarKey();
        cartService.deleteChecked(cartKey);
        return Result.ok();
    }

    /**
     * 获取当前购物车中所有选中商品
     * @return
     */
    @GetMapping("/checked/list")
    public Result<List<CartInfo>> getChecked(){
        String carKey = cartService.determinCarKey();
        List<CartInfo> cartList = cartService.getCartList(carKey);
        return Result.ok(cartList);
    }
}











