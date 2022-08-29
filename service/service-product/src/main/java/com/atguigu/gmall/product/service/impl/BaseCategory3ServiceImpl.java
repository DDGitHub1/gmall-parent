package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/8/23
 * @Description : com.atguigu.gmall.product.service.impl
 * @Version : 1.0
 */
@Service
public class BaseCategory3ServiceImpl extends ServiceImpl<BaseCategory3Mapper, BaseCategory3> implements BaseCategory3Service {
    @Autowired
    BaseCategory3Mapper baseCategory3Mapper;
    @Override
    public List<BaseCategory3> getCategory3Child(Long c2Id) {
        QueryWrapper<BaseCategory3> wrapper = new QueryWrapper();
        wrapper.eq("category2_id",c2Id);
        List<BaseCategory3> list = baseCategory3Mapper.selectList(wrapper);
        return list;
    }

    @Override
    public CategoryViewTo getCategoryView(Long c3Id) {
        CategoryViewTo categoryView = baseCategory3Mapper.getCategoryView(c3Id);
        return categoryView;
    }
}
