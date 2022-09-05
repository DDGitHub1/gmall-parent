package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.product.mapper.BaseCategory2Mapper;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import com.atguigu.starter.cache.annotation.GmallCache;
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
public class BaseCategory2ServiceImpl extends ServiceImpl<BaseCategory2Mapper, BaseCategory2> implements BaseCategory2Service {
    @Autowired
    BaseCategory2Mapper baseCategory2Mapper;

    /**
     * 查询1级分类下的所有二级分类
     * @param c1Id
     * @return
     */
    @Override
    public List<BaseCategory2> getCategory1Child(Long c1Id) {
        QueryWrapper<BaseCategory2> wrapper = new QueryWrapper<>();
        wrapper.eq("category1_id",c1Id);
        //查询1级分类下的所有二级分类
        List<BaseCategory2> list = baseCategory2Mapper.selectList(wrapper);
        return list;
    }

    /**
     * 查询所有分类以及下面的子分类 并组装成树形结构
     * @return
     */
    @GmallCache(cacheKey = SysRedisConst.CACHE_CATEGORYS)
    @Override
    public List<CategoryTreeTo> getAllCategoryWithTree() {
        System.out.println("查询三级分类树形数据");
        List<CategoryTreeTo> categoryTreeTos = baseCategory2Mapper.getAllCategoryWithTree();

        return categoryTreeTos;
    }
}
