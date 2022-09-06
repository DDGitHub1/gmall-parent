package com.atguigu.gmall.item.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.search.SearchFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.to.SkuDetailTo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : dyh
 * @Date: 2022/8/26
 * @Description : com.atguigu.gmall.item.api
 * @Version : 1.0
 */
@Api(tags = "三级分类的RPC接口")
@RequestMapping("/api/inner/rpc/item")
@RestController
public class SkuDetailApiController {

    @Autowired
    SkuDetailService detailService;
    @Autowired
    SearchFeignClient searchFeignClient;

    @GetMapping("/skudetail/{skuId}")
    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId")Long skuId){
        //商品的详情
        SkuDetailTo skuDetailTo = detailService.getSkuDetail(skuId);
        //更新热度分
        detailService.updateHotScore(skuId);
        return Result.ok(skuDetailTo);
    }
}
