package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/1
 * @Description : com.atguigu.gmall.product.bloom.impl
 * @Version : 1.0
 */

@Service
public class BloomOpsServiceImpl implements BloomOpsService {
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    SkuInfoService skuInfoService;
    /**
     * 重建布隆
     * @param bloomName
     */
    @Override
    public void rebuildBloom(String bloomName, BloomDataQueryService dataQueryService) {
        RBloomFilter<Object> oldBloomFilter = redissonClient.getBloomFilter(bloomName);
        String newBloomName = bloomName + "_new";
        //1.先准备一个新的波隆过滤器，所有的东西都初始化好
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(newBloomName);
        //2.拿到所有的商品id
        List<Long> allSkuId = skuInfoService.findAllSkuId();
        //3.初始化新的布隆
        bloomFilter.tryInit(5000000, 0.00001);
        for (Long skuId : allSkuId) {
            bloomFilter.add(skuId);
        }
        //4.新布隆就绪
        //ob bb nb  交换
        //5、两个交换；nb 要变成 ob。 大数据量的删除会导致redis卡死
        //最极致的做法：lua。 自己尝试写一下这lua脚本
        oldBloomFilter.rename("bbbb_bloom");//老布隆下线
        bloomFilter.rename(bloomName);
        //6.删除老布隆、和中间交换曾层
        oldBloomFilter.deleteAsync();
        redissonClient.getBloomFilter("bbbb_bloom").deleteAsync();
    }
}
