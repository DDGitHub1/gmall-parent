package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : dyh
 * @Date: 2022/8/25
 * @Description : com.atguigu.gmall.product.controller
 * @Version : 1.0
 */
@RequestMapping("/admin/product")
@RestController
public class SkuController {
    @Autowired
    SkuInfoService skuInfoService;
    @GetMapping("/list/{pn}/{ps}")
    public Result getSkuList(@PathVariable("pn") Long pn,
                             @PathVariable("ps") Long ps){

        Page<SkuInfo> page = new Page<>(pn,ps);
        Page<SkuInfo> page1 = skuInfoService.page(page);
        return Result.ok(page1);
    }

    //saveSkuInfo
    @PostMapping("/saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo info){
        //sku大保存
        skuInfoService.saveSkuInfo(info);
        return Result.ok();
    }

    //cancelSale/40

    /**
     * 商品下架
     * @param skuId
     * @return
     */
    @GetMapping("/cancelSale/{skuId}")
    public Result cancelSale(@PathVariable("skuId")Long skuId){
        skuInfoService.cancelSale(skuId);
        return Result.ok();
    }
    /**
     * 商品上架
     * @return
     */
    @GetMapping("/onSale/{skuId}")
    public Result onSale(@PathVariable("skuId")Long skuId){
        skuInfoService.onSale(skuId);
        return Result.ok();
    }

}
