package com.atguigu.gmall.user.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author : dyh
 * @Date: 2022/9/7
 * @Description : com.atguigu.gmall.user.filter
 * @Version : 1.0
 */
public class HelloFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        //目标方法执行之前
        HttpServletRequest resp = (HttpServletRequest) response;
        //放行
        chain.doFilter(request,response);
        // 目标方法执行之后
    }
}
