package com.atguigu.gmall.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @Author : dyh
 * @Date: 2022/8/30
 * @Description : com.atguigu.gmall.item
 * @Version : 1.0
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Test
    public void redisTest(){
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        forValue.set("hello","world");
        System.out.println("存入redis");
        String hello = forValue.get("hello");
        System.out.println("获取hello的值："+hello);
    }
}
