package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.feign.SkuDetailFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author : dyh
 * @Date: 2022/8/26
 * @Description : com.atguigu.gmall.item.service.impl
 * @Version : 1.0
 */
@Service
public class SkuDetailServiceImpl implements SkuDetailService {
    @Autowired
    SkuDetailFeignClient skuDetailFeignClient;
    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
//        SkuDetailTo detailTo = new SkuDetailTo();
        //远程调用商品服务查询
        Result<SkuDetailTo> skuDetail = skuDetailFeignClient.getSkuDetail(skuId);
        return skuDetail.getData();
    }
}
