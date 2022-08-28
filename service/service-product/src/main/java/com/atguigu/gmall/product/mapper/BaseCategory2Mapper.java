package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/8/23
 * @Description : com.atguigu.gmall.product.mapper
 * @Version : 1.0
 */
public interface BaseCategory2Mapper extends BaseMapper<BaseCategory2> {
    List<CategoryTreeTo> getAllCategoryWithTree();
}
