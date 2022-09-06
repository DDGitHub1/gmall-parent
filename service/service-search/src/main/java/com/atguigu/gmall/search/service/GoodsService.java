package com.atguigu.gmall.search.service;

import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.vo.search.SearchParamVo;
import com.atguigu.gmall.model.vo.search.SearchResponseVo;

/**
 * @Author : dyh
 * @Date: 2022/9/5
 * @Description : com.atguigu.gmall.search.service
 * @Version : 1.0
 */
public interface GoodsService {
    void save(Goods goods);

    void deleteGoods(Long skuId);

    SearchResponseVo search(SearchParamVo paramVo);

    void updateHotScore(Long skuId, Long score);
}
