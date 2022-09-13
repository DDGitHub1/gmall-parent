package com.atguigu.gmall.common.config.annotation;

import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author : dyh
 * @Date: 2022/9/8
 * @Description : com.atguigu.gmall.common.config.annotation
 * @Version : 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(GlobalExceptionHandler.class)
public @interface EnableAutoExceptionHandler {
}
