package com.atguigu.gmall.search.repository;

import com.atguigu.gmall.model.list.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author : dyh
 * @Date: 2022/9/5
 * @Description : com.atguigu.gmall.search.repository
 * @Version : 1.0
 */
@Repository
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
