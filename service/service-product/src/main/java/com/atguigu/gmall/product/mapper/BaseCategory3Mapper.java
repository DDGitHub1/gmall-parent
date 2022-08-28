package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author : dyh
 * @Date: 2022/8/23
 * @Description : com.atguigu.gmall.product.mapper
 * @Version : 1.0
 */
public interface BaseCategory3Mapper extends BaseMapper<BaseCategory3> {
    /**
     * 根据三级分类id查询对应的二级 以及夫分类
     * @param category3Id
     * @return
     */
    CategoryViewTo getCategoryView(@Param("category3Id") Long category3Id);
}
