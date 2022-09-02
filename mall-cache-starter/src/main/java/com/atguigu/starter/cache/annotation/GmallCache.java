package com.atguigu.starter.cache.annotation;

import java.lang.annotation.*;

/**
 * @Author : dyh
 * @Date: 2022/9/1
 * @Description : com.atguigu.gmall.item.annotation
 * @Version : 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GmallCache {

    String cacheKey() default ""; //就是cacheKey

    String bloomName() default ""; //布隆

    String bloomValue() default "";//指定布隆过滤器

    String lockName() default "lock:glabol";
}
