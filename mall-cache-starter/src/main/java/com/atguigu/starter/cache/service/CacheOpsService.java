package com.atguigu.starter.cache.service;

import java.lang.reflect.Type;

/**
 * @Author : dyh
 * @Date: 2022/8/31
 * @Description : com.atguigu.gmall.item.cache
 * @Version : 1.0
 */
public interface CacheOpsService {
    /**
     * 从缓存中获取一个json并转为普通对象
     * @param cacheKey
     * @param clz
     * @param <T>
     * @return
     */
    <T>T getCacheData(String cacheKey, Class<T> clz);


    /**
     * 从缓存中获取一个json并转为复杂对象
     * @param cacheKey
     * @param type
     * @return
     */
    Object getCacheData(String cacheKey,
                        Type type);




    /**
     * 布隆过滤器判断是否有这个商品
     * @param skuId
     * @return
     */
    boolean bloomContains(Object skuId);
    /**
     *判断指定布隆过滤器(bloomName) 是否包含 指定值（bVal）
     * @param bloomName
     * @param bVal
     * @return
     */
    boolean bloomContains(String bloomName, Object bVal);




    /**
     *给指定的商品加锁
     * @param skuId
     * @return
     */
    boolean tryLock(Long skuId);
    /**
     *
     * @param lockName
     * @return
     */
    boolean tryLock(String lockName);

    /**
     * 把指定对象使用指定key保存到缓存中
     * @param cacheKey
     * @param fromRpc
     */
    void saveData(String cacheKey, Object fromRpc);

    /**
     * 解锁
     * @param skuId
     */
    void unlock(Long skuId);


    /**
     * 解指定的锁
     * @param lockName
     */
    void unlock(String lockName);
}
