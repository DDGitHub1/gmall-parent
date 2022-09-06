package com.atguigu.gmall.model.vo.user;

import lombok.Data;

/**
 * @Author : dyh
 * @Date: 2022/9/6
 * @Description : com.atguigu.gmall.model.vo.search.user
 * @Version : 1.0
 */
@Data
public class LoginSuccessVo {
    private String token; //用户的令牌。
    private String nickName; //用户
}
