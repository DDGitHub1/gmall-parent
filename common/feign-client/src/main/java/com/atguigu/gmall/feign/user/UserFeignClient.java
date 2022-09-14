package com.atguigu.gmall.feign.user;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/13
 * @Description : com.atguigu.gmall.feign.user
 * @Version : 1.0
 */
@RequestMapping("/api/inner/rpc/user")
@FeignClient("service-user")
public interface UserFeignClient {

    /**
     * 获取用户所有的收获地址列表
     * @return
     */
    @GetMapping("/address/list")
    public Result<List<UserAddress>> getUserAddressList();
}
