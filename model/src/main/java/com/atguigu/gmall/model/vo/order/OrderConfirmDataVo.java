package com.atguigu.gmall.model.vo.order;

import com.atguigu.gmall.model.cart.CartInfo;
import com.atguigu.gmall.model.user.UserAddress;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/13
 * @Description : com.atguigu.gmall.model.vo.order
 * @Version : 1.0
 */
@Data
public class OrderConfirmDataVo {
    //购物车中需要结算的所有商品信息
    private List<CartInfoVo> detailArrayList;

    private Integer totalNum;
    private BigDecimal totalAmount;

    //用户收货地址列表
    private List<UserAddress> userAddressList;

    //交易追踪号
    private String tradeNo;
}
