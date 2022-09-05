package com.atguigu.gmall.model.vo.search;

import lombok.Data;

/**
 * @Author : dyh
 * @Date: 2022/9/5
 * @Description : com.atguigu.gmall.model.vo.search
 * @Version : 1.0
 */

/**
 * 品牌列表检索数据
 */
@Data
public class TrademarkVo {
    private Long tmId;
    private String tmLogoUrl;
    private String tmName;
}
