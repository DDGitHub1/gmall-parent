package com.atguigu.gmall.order.service;

import com.atguigu.gmall.model.order.OrderInfo;
import com.atguigu.gmall.model.vo.order.OrderSubmitVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 杜宇浩
* @description 针对表【order_info(订单表 订单表)】的数据库操作Service
* @createDate 2022-09-12 20:18:50
*/
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 根据页面提交的数据生成一个数据库的订单
     * @param submitVo
     * @param tradeNo
     * @return
     */
    Long saveOrder(OrderSubmitVo submitVo, String tradeNo);

}
