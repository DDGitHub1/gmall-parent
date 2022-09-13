package com.atguigu.gmall.cart.controller;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.cart.CartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/8
 * @Description : com.atguigu.gmall.cart.controller
 * @Version : 1.0
 */
@RequestMapping("/api/cart")
@RestController
public class CartRestController {
    @Autowired
    CartService cartService;

    @GetMapping("/cartList")
    public Result cartList(){
        //1.决定用哪个购物车键
        String carKey = cartService.determinCarKey();
        //先尝试合并购物车
        cartService.mergeUserAndTempCart();
        //2.获取这个购物车中所有的商品
        List<CartInfo> infos = cartService.getCartList(carKey);
        return Result.ok(infos);
    }
    //api/cart/addToCart/53/1

    /**
     * 修改购物车中某个商品的数量
     * @param skuId
     * @param num
     * @return
     */
    @PostMapping("/addToCart/{skuId}/{num}")
    public Result updateItemNum(@PathVariable("skuId") Long skuId,
                                @PathVariable("num") Integer num){
        String carKey = cartService.determinCarKey();
        cartService.updateItemNum(skuId,num,carKey);
        return Result.ok();
    }
    //api/cart/checkCart/54/1
    /**
     * 修改勾选状态
     * @param skuId
     * @param status
     * @return
     */
    @GetMapping("/checkCart/{skuId}/{status}")
    public Result check(@PathVariable("skuId") Long skuId,
                            @PathVariable("status") Integer status){
        String carKey = cartService.determinCarKey();
        cartService.updateChecked(skuId,status,carKey);
        return Result.ok();
    }

    /**
     * 删除购物车中商品
     * @param skuId
     * @return
     */
    @DeleteMapping("/deleteCart/{skuId}")
    public Result deleteItem(@PathVariable("skuId") Long skuId){

        String cartKey = cartService.determinCarKey();
        cartService.deleteCartItem(skuId,cartKey);

        return Result.ok();
    }
}
