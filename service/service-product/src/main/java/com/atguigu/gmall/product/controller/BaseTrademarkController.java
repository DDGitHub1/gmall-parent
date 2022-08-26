package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
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
public class BaseTrademarkController {
    @Autowired
    BaseTrademarkService baseTrademarkService;
    /**
     *
     * @param pageSize 第几页
     * @param limit 每页数量
     * @return
     */
    @GetMapping("/baseTrademark/{pageSize}/{limit}")
    public Result baseTrademark(@PathVariable("pageSize") Long pageSize,
                                @PathVariable("limit") Long limit){

        Page<BaseTrademark> page = new Page<>();
        Page<BaseTrademark> pageResult = baseTrademarkService.page(page);
        return Result.ok(pageResult);
    }
    //根据id查询品牌
    ///baseTrademark/get/2
    @GetMapping("//baseTrademark/get/{id}")
    public Result getBaseTrademark(@PathVariable("id") Long id){
        BaseTrademark byId = baseTrademarkService.getById(id);
        return Result.ok(byId);
    }
    //修改品牌属性
    //baseTrademark/update
    @PutMapping("/baseTrademark/update")
    public Result updatebaseTrademark(@RequestBody BaseTrademark trademark){
        baseTrademarkService.updateById(trademark);
        return Result.ok();
    }
    //admin/product/baseTrademark/save
    //添加品牌信息
    @PostMapping("/baseTrademark/save")
    public Result baseTrademark(@RequestBody BaseTrademark trademark){
        baseTrademarkService.save(trademark);
        return Result.ok();
    }
    //删除品牌信息
    //baseTrademark/remove/12
    @DeleteMapping("/baseTrademark/remove/{tid}")
    public Result remove(@PathVariable("tid") Long tid){
        baseTrademarkService.removeById(tid);
        return Result.ok();
    }

    //admin/product/fileUpload 文件上传

    /**
     * 获取所有品牌
     * @return
     */
    @GetMapping("/baseTrademark/getTrademarkList")
    public Result getTrademarkList(){
        List<BaseTrademark> list = baseTrademarkService.list();
        return Result.ok(list);
    }
}
