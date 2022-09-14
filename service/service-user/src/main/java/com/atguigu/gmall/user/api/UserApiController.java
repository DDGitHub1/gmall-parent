package com.atguigu.gmall.user.api;

import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.user.UserAddress;
import com.atguigu.gmall.model.vo.user.UserAuthInfo;
import com.atguigu.gmall.user.service.UserAddressService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/13
 * @Description : com.atguigu.gmall.user.api
 * @Version : 1.0
 */
@RequestMapping("/api/inner/rpc/user")
@RestController
public class UserApiController {
    @Autowired
    UserAddressService userAddressService;
    /**
     * 获取用户所有的收获地址列表
     * @return
     */
    @GetMapping("/address/list")
    public Result<List<UserAddress>> getUserAddressList(){
        //1.获取当前登录的用户
        UserAuthInfo info = AuthUtils.getCurrentAuthInfo();
        //获取用户id
        Long userId = info.getUserId();
        //3.获取所有的收获地址
        List<UserAddress> userAddresses = userAddressService.list(new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId,userId));
        return Result.ok(userAddresses);
    }
}
