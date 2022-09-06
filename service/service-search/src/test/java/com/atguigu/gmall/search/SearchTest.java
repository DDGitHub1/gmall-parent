package com.atguigu.gmall.search;

import com.atguigu.gmall.model.vo.search.SearchParamVo;
import com.atguigu.gmall.search.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author : dyh
 * @Date: 2022/9/6
 * @Description : com.atguigu.gmall.search
 * @Version : 1.0
 */
@SpringBootTest
public class SearchTest {
    @Autowired
    GoodsService goodsService;
    @Test
    public void test1(){
        SearchParamVo vo = new SearchParamVo();
        vo.setTrademark("4:小米");
        vo.setCategory3Id(61L);
        goodsService.search(vo);
    }
}
