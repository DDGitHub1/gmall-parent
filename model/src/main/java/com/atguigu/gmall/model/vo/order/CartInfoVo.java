package com.atguigu.gmall.model.vo.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : dyh
 * @Date: 2022/9/13
 * @Description : com.atguigu.gmall.model.vo.order
 * @Version : 1.0
 */
@Data
public class CartInfoVo {
    private Long skuId;
    private String imgUrl;
    private String skuName;
    private BigDecimal orderPrice;//实时价格，最新价格
    private Integer skuNum;
    //是否有货
    private String hasStock = "1";
}
