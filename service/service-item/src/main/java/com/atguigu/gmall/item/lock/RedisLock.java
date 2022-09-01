package com.atguigu.gmall.item.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author : dyh
 * @Date: 2022/8/31
 * @Description : com.atguigu.gmall.item.lock
 * @Version : 1.0
 */
@Service
public class RedisLock {
    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @return
     */
    public String lock(){
        //1.枷锁
        //2.设置过期时间
        //setnxex
        String token = UUID.randomUUID().toString();
        while (!redisTemplate.opsForValue().setIfAbsent("lock",token,10, TimeUnit.SECONDS)){} //自旋阻塞式枷锁
        //加锁成功
        return token;
    }
    public void unlock(String token){
        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1]  then return redis.call('del',KEYS[1]); else  return 0;end;";
        redisTemplate.execute(new DefaultRedisScript<>(luaScript,Long.class), Arrays.asList("lock"),token);
    }
}
