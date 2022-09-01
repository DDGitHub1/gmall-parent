package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.service.SkuInfoService;
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
public class SkuBloomDataQueryServiceImpl implements BloomDataQueryService {
    @Autowired
    SkuInfoService skuInfoService;
    @Override
    public List queryData() {
        List<Long> allSkuId = skuInfoService.findAllSkuId();
        return allSkuId;
    }
}
