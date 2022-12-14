package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/8/23
 * @Description : com.atguigu.gmall.product.service
 * @Version : 1.0
 */
public interface BaseCategory2Service extends IService<BaseCategory2> {
    List<BaseCategory2> getCategory1Child(Long c1Id);

    /**
     * 查询所有分类以及下面的子分类 并组装成树形结构
     * @return
     */
    List<CategoryTreeTo> getAllCategoryWithTree();
}
