package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseCategory3;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/8/23
 * @Description : com.atguigu.gmall.product.service
 * @Version : 1.0
 */
public interface BaseCategory3Service extends IService<BaseCategory3> {
    List<BaseCategory3> getCategory3Child(Long c2Id);
}
