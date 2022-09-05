package com.atguigu.gmall.model.vo.search;

import lombok.Data;

/**
 * @Author : dyh
 * @Date: 2022/9/5
 * @Description : com.atguigu.gmall.model.vo.search
 * @Version : 1.0
 */

/**
 * 封装检索条件
 */
@Data
public class SearchParamVo {
    Long category3Id;
    Long category1Id;
    Long Category2Id;
    String keyword;
    String trademark;
    String[] props;
    String order = "1:desc";
    Integer pageNo = 1;
}
