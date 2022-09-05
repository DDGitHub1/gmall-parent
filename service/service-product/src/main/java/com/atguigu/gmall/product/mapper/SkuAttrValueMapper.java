package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.SkuAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 杜宇浩
* @description 针对表【sku_attr_value(sku平台属性值关联表)】的数据库操作Mapper
* @createDate 2022-08-23 18:36:27
* @Entity com.atguigu.gmall.product.domain.SkuAttrValue
*/
public interface SkuAttrValueMapper extends BaseMapper<SkuAttrValue> {

    /**
     * 查询当前sku所有的平台属性
     * @param skuId
     * @return
     */
    List<SearchAttr> getSkuAttrNameAndValue(Long skuId);

}




