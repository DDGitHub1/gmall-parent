package com.atguigu.gmall.constant;

import java.util.Map;

/**
 * @Author : dyh
 * @Date: 2022/9/15
 * @Description : com.atguigu.gmall.constant
 * @Version : 1.0
 */
public class MqConst {
    //订单交换机名
    public static final String EXCHANGE_ORDER_EVNT = "order-event-exchange";

    //订单延迟队列
    public static final String QUEUE_ORDER_DELAY = "order-delay-queue";

    //订单死信路由键
    public static final String RK_ORDER_DEAD = "order.dead";

    //订单新建路由键
    public static final String RK_ORDER_CREATED = "order.created";

    //死单队列
    public static final String QUEUE_ORDER_DEAD = "order-dead-queue";

}
