package com.atguigu.gmall.item.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.lock.RedisLock;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author : dyh
 * @Date: 2022/8/31
 * @Description : com.atguigu.gmall.item.api
 * @Version : 1.0
 */
@RequestMapping("/lock")
@RestController
public class LockTestController {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RedissonClient redissonClient;
    ReentrantLock lock = new ReentrantLock();
    @Autowired
    RedisLock redisLock;

    int i = 0;


    /**
     * 闭锁
     */
    @GetMapping("/longzhu")
    public Result shoujilongzhu(){

        RCountDownLatch latch = redissonClient.getCountDownLatch("sl-lock");
        latch.countDown();
        return Result.ok("收集到1颗");
    }

    /**
     * 召唤神龙
     */
    @GetMapping("/shenlong")
    public Result shenlong() throws InterruptedException {
        RCountDownLatch latch = redissonClient.getCountDownLatch("sl-lock");
        latch.trySetCount(7); //设置数量


        latch.await(); //等待
        return Result.ok("一条龙 来了....");
    }


    /**
     * 读写锁
     * 写数据
     */
    @GetMapping("/rw/write")
    public Result readWriteValue() throws InterruptedException {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        //获取写锁
        RLock rLock = lock.writeLock();
        //加写锁
        rLock.lock();
        //业务正在改数据
        Thread.sleep(20000);
        i=888;
        rLock.unlock();
        return Result.ok();
    }
    /**
     * 读数据
     */
    @GetMapping("/rw/read")
    public Result readValue(){
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        RLock rLock = lock.readLock();
        rLock.lock();
        int x = i;
        rLock.unlock();
        return Result.ok();
    }

    /**
     * Redisson加的锁
     * 1、名相同就是同一把锁
     * 2、锁有自动续期机制，默认是30s过期，如果业务超长会自动每过10s，就续满期
     * 3、加锁+过期时间 = 加锁原子     得到锁值+判断+删除锁 = 删锁原子
     * 4、redisson底层的所有操作都是lua脚本完成，是原子
     * Redisson怎么对锁进行续期的？
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/common")
    public Result redissonLock() throws InterruptedException {
        //名字相同就代表同一把锁
        //1、得到一把锁
        RLock lock = redissonClient.getLock("lock-hello");//获取一个普通的锁；可重入锁
        //2.加锁
        //2.1 lock()
//        lock.lock(10, TimeUnit.SECONDS);
        lock.lock();//阻塞式加锁，非要等到锁。默认30s的过期时间

        System.out.println("得到锁");
        //执行业务逻辑
        Thread.sleep(5000);
        System.out.println("执行结束");
        //3.解锁
        lock.unlock();
        return Result.ok();
    }

    @GetMapping("/incr")
    public Result increment(){
//        lock.lock();
        String token = redisLock.lock();
        String a = redisTemplate.opsForValue().get("a");
        int i = Integer.parseInt(a);
        //业务计算
        i++;
        redisTemplate.opsForValue().set("a",i+"");
//        lock.unlock();
        redisLock.unlock(token);
        return Result.ok();
    }
}
