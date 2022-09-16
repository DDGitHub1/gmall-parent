package com.atguigu.gmall.model.to.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : dyh
 * @Date: 2022/9/15
 * @Description : com.atguigu.gmall.model.to.mq
 * @Version : 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderMsg {
    private Long orderId;
    private Long userId;
}
