package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : dyh
 * @Date: 2022/8/31
 * @Description : com.atguigu.gmall.product.api
 * @Version : 1.0
 */
/**
 * 组件默认是单实例，Controller一直在
 */
@RestController
public class HelloController {
    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/bloom/contains/{skuId}")
    public Result bloomContains(@PathVariable("skuId") Long skuId){

        //1、拿到布隆过滤器
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);

        boolean contains = filter.contains(skuId);

        return Result.ok("布隆有吗？"+contains);
    }
}
