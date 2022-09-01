package com.atguigu.gmall.product.bloom;

/**
 * @Author : dyh
 * @Date: 2022/9/1
 * @Description : com.atguigu.gmall.product.bloom
 * @Version : 1.0
 */
public interface BloomOpsService {
    /**
     * 重建指定布隆
     * @param bloomName
     */
    void rebuildBloom(String bloomName,BloomDataQueryService dataQueryService);
}
