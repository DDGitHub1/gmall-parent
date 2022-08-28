package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/8/23
 * @Description : com.atguigu.gmall.product.controller
 * @Version : 1.0
 */
@RequestMapping("/admin/product")
@RestController
public class BaseAttrController {
//    admin/product/attrInfoList/2/0/0

    @Autowired
    BaseAttrInfoService baseAttrInfoService;
    @Autowired
    BaseAttrValueService baseAttrValueService;

    @GetMapping("/attrInfoList/{c1Id}/{c2Id}/{c3Id}")
    public Result getAttrInfoList(
            @PathVariable("c1Id") Long c1Id,
            @PathVariable("c2Id") Long c2Id,
            @PathVariable("c3Id") Long c3Id
    ){
    List<BaseAttrInfo> infos = baseAttrInfoService.getAttrInfoAndValueByCategoryId(c1Id,c2Id,c3Id);
    return Result.ok(infos);
    }

    /**
     * 保存、修改属性信息二合一的方法
     * 前端把所有页面录入的数据以json的方式post传给我们
     * 请求体：
     *  {"id":null,"attrName":"出厂日期","category1Id":0,"category2Id":0,"category3Id":0,"attrValueList":[{"valueName":"2019","edit":false},{"valueName":"2020","edit":false},{"valueName":"2021","edit":false},{"valueName":"2022","edit":false}],"categoryId":2,"categoryLevel":1}
     *
     *  取出前端发送的请求的请求体中的数据 @RequestBody，
     *  并把这个数据(json)转成指定的BaseAttrInfo对象，
     *  BaseAttrInfo封装前端提交来的所有数据
     *
     *
     */
    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){

        baseAttrInfoService.saveAttrInfo(baseAttrInfo);
        return Result.ok();
    }

    //http://api.gmall.com/admin/product/getAttrValueList/1
    @GetMapping("/getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable("attrId") Long attrId){
       List<BaseAttrValue> values = baseAttrValueService.getAttrValueList(attrId);
        return Result.ok(values);
    }


}
