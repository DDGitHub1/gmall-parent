package com.atguigu.gmall.feign.search;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.vo.search.SearchParamVo;
import com.atguigu.gmall.model.vo.search.SearchResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : dyh
 * @Date: 2022/9/5
 * @Description : com.atguigu.gmall.feign.search
 * @Version : 1.0
 */
@RequestMapping("/api/inner/rpc/search")
@FeignClient("service-search")
public interface SearchFeignClient {

    /**
     * 保存商品信息到es
     * @param goods
     * @return
     */
    @PostMapping("/goods")
    public Result saveGoods(@RequestBody Goods goods);

    /**
     * 下架商品 es
     * @param skuId
     * @return
     */
    @DeleteMapping("/goods/{skuId}")
    public Result deleteGoods(@PathVariable("skuId") Long skuId);

    /**
     * 商品检索
     * @param paramVo
     * @return
     */
    @PostMapping("/goods/search")
    public Result<SearchResponseVo>  search(@RequestBody SearchParamVo paramVo);
}
