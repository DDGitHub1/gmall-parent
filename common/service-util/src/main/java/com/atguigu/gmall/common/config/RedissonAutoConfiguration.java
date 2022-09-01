package com.atguigu.gmall.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : dyh
 * @Date: 2022/8/31
 * @Description : com.atguigu.gmall.item.config
 * @Version : 1.0
 */
@AutoConfigureAfter(RedisProperties.class)
@Configuration
public class RedissonAutoConfiguration {
    @Autowired
    RedisProperties redisProperties;
    @Bean
    public RedissonClient redissonClient(){
        //1、创建一个配置
        Config config = new Config();
        String host = redisProperties.getHost();
        int port = redisProperties.getPort();
        String password = redisProperties.getPassword();
        //2、指定好redisson的配置项
        config.useSingleServer()
                .setAddress("redis://"+host+":"+port)
                .setPassword(password);

        //3、创建一个 RedissonClient
        RedissonClient client = Redisson.create(config);
        //Redis url should start with redis:// or rediss:// (for SSL connection)


        return client;
    }
}
