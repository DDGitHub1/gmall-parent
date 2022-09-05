package com.atguigu.gmall.model.vo.search;

import lombok.Data;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/5
 * @Description : com.atguigu.gmall.model.vo.search
 * @Version : 1.0
 */
@Data
public class AttrVo {
    private Long attrId;
    private String attrName;
    //每个属性涉及到的所有值
    private List<String> attrValueList;
}
