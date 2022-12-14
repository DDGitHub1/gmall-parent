package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SkuImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 杜宇浩
* @description 针对表【sku_image(库存单元图片表)】的数据库操作Service
* @createDate 2022-08-23 18:36:27
*/
public interface SkuImageService extends IService<SkuImage> {

    /**
     * 查询某个sku的所有图片
     * @param skuId
     * @return
     */
    List<SkuImage> getSkuImage(Long skuId);
}
