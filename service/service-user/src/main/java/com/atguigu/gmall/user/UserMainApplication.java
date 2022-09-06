package com.atguigu.gmall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author : dyh
 * @Date: 2022/9/6
 * @Description : com.atguigu.gmall.user
 * @Version : 1.0
 */
@MapperScan("com.atguigu.gmall.user.mapper")
@SpringCloudApplication
public class UserMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserMainApplication.class,args);
    }
}
