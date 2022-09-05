package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.product.CategoryFeignClient;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/8/26
 * @Description : com.atguigu.gmall.web.controller
 * @Version : 1.0
 */
@Controller
public class IndexController {
    @Autowired
    CategoryFeignClient categoryFeignClient;
    /**
     * 跳转首页
     * @return
     */
    @GetMapping({"/","/index"})
    public String indexPage(Model model){
        //TODO 远程查询出所有菜单。封装成一个树形结构的模型
        Result<List<CategoryTreeTo>> result = categoryFeignClient.getAllCategoryWithTree();
        if(result.isOk()){
            //远程成功。 强类型语言
            List<CategoryTreeTo> data = result.getData();
            model.addAttribute("list",data);
        }
        return "index/index";//页面逻辑视图层
    }
}
