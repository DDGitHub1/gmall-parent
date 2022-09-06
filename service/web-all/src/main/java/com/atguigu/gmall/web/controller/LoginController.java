package com.atguigu.gmall.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : dyh
 * @Date: 2022/9/6
 * @Description : com.atguigu.gmall.web.controller
 * @Version : 1.0
 */
@Controller
public class LoginController {
    /**
     * 登录页
     * @return
     */
    @GetMapping("/login.html")
    public String loginPage(@RequestParam("originUrl") String originUrl,
                            Model model){
        model.addAttribute("originUrl",originUrl);
        return "login";
    }
}
