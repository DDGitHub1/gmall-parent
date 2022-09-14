package com.atguigu.gmall.order;

import com.atguigu.gmall.common.config.annotation.EnableAutoExceptionHandler;
import com.atguigu.gmall.common.config.annotation.EnableAutoFeignInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author : dyh
 * @Date: 2022/9/12
 * @Description : com.atguigu.gmall.order
 * @Version : 1.0
 */

@EnableTransactionManagement
@EnableAutoExceptionHandler
@EnableAutoFeignInterceptor //开启feign 用户id透传拦截器
@EnableFeignClients({
        "com.atguigu.gmall.feign.cart",
        "com.atguigu.gmall.feign.user",
        "com.atguigu.gmall.feign.product",
        "com.atguigu.gmall.feign.ware"
})
@MapperScan("com.atguigu.gmall.order.mapper")
@SpringCloudApplication
public class OrderMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainApplication.class,args);
    }
}
