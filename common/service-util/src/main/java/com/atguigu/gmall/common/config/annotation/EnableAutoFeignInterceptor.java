package com.atguigu.gmall.common.config.annotation;

import com.atguigu.gmall.common.config.FeignInterceptorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author : dyh
 * @Date: 2022/9/12
 * @Description : com.atguigu.gmall.common.config.annotation
 * @Version : 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FeignInterceptorConfiguration.class)
public @interface EnableAutoFeignInterceptor {
}
