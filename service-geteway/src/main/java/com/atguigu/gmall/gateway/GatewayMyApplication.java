package com.atguigu.gmall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author : dyh
 * @Date: 2022/8/22
 * @Description : com.atguigu.gmall.gateway
 * @Version : 1.0
 */
/**
 * 主启动类
 */
//@EnableCircuitBreaker  //开启服务熔断降级、流量保护 [1、导入jar  2、使用这个注册]
//@EnableDiscoveryClient //开启服务发现[1、导入服务发现jar  2、使用这个注解]
//@SpringBootApplication

@SpringCloudApplication  //以上的合体
public class GatewayMyApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMyApplication.class,args);
    }
}
