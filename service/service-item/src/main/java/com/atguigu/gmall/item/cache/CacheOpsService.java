package com.atguigu.gmall.item.cache;

import com.atguigu.gmall.model.to.SkuDetailTo;

/**
 * @Author : dyh
 * @Date: 2022/8/31
 * @Description : com.atguigu.gmall.item.cache
 * @Version : 1.0
 */
public interface CacheOpsService {
    <T>T getCacheData(String cacheKey, Class<T> clz);

    /**
     * 布隆过滤器判断是否有这个商品
     * @param skuId
     * @return
     */
    boolean bloomContains(Long skuId);

    /**
     *给指定的商品加锁
     * @param skuId
     * @return
     */
    boolean tryLock(Long skuId);

    /**
     * 把指定对象使用指定key保存到缓存中
     * @param cacheKey
     * @param fromRpc
     */
    void saveData(String cacheKey, SkuDetailTo fromRpc);

    /**
     * 解锁
     * @param skuId
     */
    void unlock(Long skuId);
}
