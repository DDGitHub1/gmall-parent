package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/8/26
 * @Description : com.atguigu.gmall.product.api
 * @Version : 1.0
 */

/**
 * 分类有关的api 以后所有远程调用都是内部接口 命名规范 /api/inner/rpc/跟模块/路径
 */
@RequestMapping("/api/inner/rpc/product")
@RestController
public class CategoryApiController {

    @Autowired
    BaseCategory2Service baseCategory2Service;
    /**
     * 查询所有数据分装成树形菜单接口
     * @return
     */
    @GetMapping("/category/tree")
    public Result getAllCategoryWithTree(){
       List<CategoryTreeTo> categoryTreeTos = baseCategory2Service.getAllCategoryWithTree();
        return Result.ok(categoryTreeTos);
    }
}
