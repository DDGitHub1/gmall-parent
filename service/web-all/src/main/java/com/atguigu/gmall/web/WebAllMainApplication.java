package com.atguigu.gmall.web;

import com.atguigu.gmall.common.config.annotation.EnableAutoFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author : dyh
 * @Date: 2022/8/26
 * @Description : com.atguigu.gmall.web
 * @Version : 1.0
 */

/**
 * 不启用数据源的配置
 * 1 datasourceAutoConfiguration
 */
//@EnableFeignClients(
//
// "com.atguigu.gmall.feign.item",
//        "com.atguigu.gmall.feign.product",
//        "com.atguigu.gmall.feign.search",
//        "com.atguigu.gmall.feign.cart",
//        "com.atguigu.gmall.feign.order")
@EnableAutoFeignInterceptor
@SpringCloudApplication
@EnableFeignClients(basePackages = {
        "com.atguigu.gmall.feign"
})
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@EnableDiscoveryClient
//@EnableCircuitBreaker
public class WebAllMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAllMainApplication.class,args);
    }
}

