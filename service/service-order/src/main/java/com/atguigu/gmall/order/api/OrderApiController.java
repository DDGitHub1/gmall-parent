package com.atguigu.gmall.order.api;

import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.vo.order.OrderConfirmDataVo;
import com.atguigu.gmall.model.vo.user.UserAuthInfo;
import com.atguigu.gmall.order.biz.OrderBizService;
import com.atguigu.gmall.order.service.OrderInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : dyh
 * @Date: 2022/9/13
 * @Description : com.atguigu.gmall.order.api
 * @Version : 1.0
 */
@RequestMapping("/api/inner/rpc/order")
@RestController
public class OrderApiController {

    @Autowired
    OrderBizService orderBizService;
    @Autowired
    OrderInfoService orderInfoService;
    /**
     *获取订单确认页所需要的数据
     * @return
     */
    @GetMapping("/confirm/data")
    public Result<OrderConfirmDataVo> getOrderConfirmData(){
        OrderConfirmDataVo vo = orderBizService.getConfirmData();
        //
        return Result.ok(vo);
    }

    /**
     * 获取某个订单数据
     * @param orderId
     * @return
     */
    @GetMapping("/info/{orderId}")
    public Result<OrderInfo> getOrderInfo(@PathVariable("orderId") Long orderId){
        UserAuthInfo authInfo = AuthUtils.getCurrentAuthInfo();
        Long userId = authInfo.getUserId();
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getId, orderId)
                .eq(OrderInfo::getUserId,userId);
        OrderInfo one = orderInfoService.getOne(queryWrapper);
        return Result.ok(one);
    }
}
