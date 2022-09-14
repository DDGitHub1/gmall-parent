package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.order.OrderFeignClient;
import com.atguigu.gmall.model.order.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Author : dyh
 * @Date: 2022/9/14
 * @Description : com.atguigu.gmall.web.controller
 * @Version : 1.0
 */
@Controller
public class PayController {

    @Autowired
    OrderFeignClient orderFeignClient;
    ///pay.html?orderId=776880038489358336
    @GetMapping("/pay.html")
    public String pay(Model model,
                      @RequestParam("orderId") Long orderId){
        Result<OrderInfo> orderInfo = orderFeignClient.getOrderInfo(orderId);
        Date ttl = orderInfo.getData().getExpireTime();
        Date cur = new Date();
        if (cur.before(ttl)) {
            //订单未过期 可以展示支付页
            model.addAttribute("orderInfo",orderInfo.getData());
            return "payment/pay";
        }
        return "payment/error";

    }
}
