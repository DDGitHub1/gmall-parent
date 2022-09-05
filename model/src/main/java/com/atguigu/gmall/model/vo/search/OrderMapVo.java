package com.atguigu.gmall.model.vo.search;

import lombok.Data;

/**
 * @Author : dyh
 * @Date: 2022/9/5
 * @Description : com.atguigu.gmall.model.vo.search
 * @Version : 1.0
 */
@Data
public class OrderMapVo {
    //排序类型 1是综合 2 是价格
    private String type;
    private String sort;
}
