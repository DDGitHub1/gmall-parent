package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 杜宇浩
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Service
* @createDate 2022-08-23 18:36:27
*/
public interface SpuSaleAttrService extends IService<SpuSaleAttr> {

    List<SpuSaleAttr> getSaleAttrAndValueBySpuId(Long spuId);

    /**
     * 查询sku对应的spu定义的所有销售属性名和值。并且标记出当前sku是哪个
     * @param spuId
     * @param skuId
     * @return
     */
    List<SpuSaleAttr> getSaleAttrAndValueMarkSku(Long spuId, Long skuId);

    String getAllSkuSaleAttrValueJson(Long spuId);
}
