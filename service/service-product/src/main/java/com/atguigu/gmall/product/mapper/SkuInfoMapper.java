package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
/**
* @author 杜宇浩
* @description 针对表【sku_info(库存单元表)】的数据库操作Mapper
* @createDate 2022-08-23 18:36:27
* @Entity com.atguigu.gmall.product.domain.SkuInfo
*/
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    void updateIsSale(@Param("skuId")Long skuId,@Param("sale") int sal);

    /**
     * 查询一个商品的实时价格
     * @param skuId
     * @return
     */
    BigDecimal getRealPrice(@Param("skuId") Long skuId);

    List<Long> getAllSkuId();

//    SkuInfo get1010Price(@Param("skuId") Long skuId);
}




