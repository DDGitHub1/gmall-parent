package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.SkuAttrValue;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SkuSaleAttrValue;
import com.atguigu.gmall.product.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.product.service.SkuAttrValueService;
import com.atguigu.gmall.product.service.SkuImageService;
import com.atguigu.gmall.product.service.SkuSaleAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import jdk.nashorn.internal.ir.IfNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
* @author 杜宇浩
* @description 针对表【sku_info(库存单元表)】的数据库操作Service实现
* @createDate 2022-08-23 18:36:27
*/
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
    implements SkuInfoService{

    @Autowired
    SkuImageService skuImageService;
    @Autowired
    SkuAttrValueService skuAttrValueService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Override
    public void saveSkuInfo(SkuInfo info) {
        //1.sku 基本信息保存到 sku_info
        save(info);
        Long id = info.getId();
        //2 sku的图片信息保存到sku_image
        for (SkuImage skuImage : info.getSkuImageList()) {
            skuImage.setSkuId(id);
        }
        skuImageService.saveBatch(info.getSkuImageList());
        //3、sku的平台属性名和值的关系保存到 sku_attr_value
        List<SkuAttrValue> attrValueList = info.getSkuAttrValueList();
        for (SkuAttrValue skuAttrValue : attrValueList) {
            skuAttrValue.setSkuId(id);
        }
        skuAttrValueService.saveBatch(attrValueList);
        //3、sku的销售属性名和值的关系保存到 sku_attr_value
        List<SkuSaleAttrValue> skuSaleAttrValueList = info.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            skuSaleAttrValue.setSkuId(id);
            skuSaleAttrValue.setSpuId(info.getSpuId());
        }
        skuSaleAttrValueService.saveBatch(skuSaleAttrValueList);
    }

    @Override
    public void cancelSale(Long skuId) {
        //1、改数据库 sku_info 这个skuId的is_sale； 1上架  0下架
        skuInfoMapper.updateIsSale(skuId, 0);
        //TODO 2、从es中删除这个商品
    }

    @Override
    public void onSale(Long skuId) {
        skuInfoMapper.updateIsSale(skuId,1);
        //TODO 2、从es中删除这个商品
    }


}




