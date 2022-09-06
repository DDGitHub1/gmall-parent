package com.atguigu.gmall.item.service;

import com.atguigu.gmall.model.to.SkuDetailTo;

/**
 * @Author : dyh
 * @Date: 2022/8/26
 * @Description : com.atguigu.gmall.item.service
 * @Version : 1.0
 */
public interface SkuDetailService {
    SkuDetailTo getSkuDetail(Long skuId);

    /**
     * 更新商品热度
     * @param skuId
     */
    void updateHotScore(Long skuId);
}
