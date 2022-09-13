package com.atguigu.gmall.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/7
 * @Description : com.atguigu.gmall.gateway.properties
 * @Version : 1.0
 */
@Data
@ConfigurationProperties(prefix = "app.auth")
@Configuration
public class AuthUrlProperties {
    List<String> noAuthUrl; //无需登录即可访问
    List<String>  loginAuthUrl; //必须登录才能访问
    String loginPage; //登录页地址
    List<String> denyUrl; //永远拒绝浏览器访问
}
