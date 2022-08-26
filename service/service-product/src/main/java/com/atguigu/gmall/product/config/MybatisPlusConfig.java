package com.atguigu.gmall.product.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author : dyh
 * @Date: 2022/8/24
 * @Description : com.atguigu.gmall.product.config
 * @Version : 1.0
 */
@EnableTransactionManagement // 开启事务注解
@Configuration //分页配置
public class MybatisPlusConfig {
    //1、把MybatisPlus的插件主体（总插件）放到容器
    @Bean
    public MybatisPlusInterceptor interceptor(){
        //插件主体
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //加入内部的小插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        //页码溢出以后，默认就访问最后一页即可
        paginationInnerInterceptor.setOverflow(true);
        //分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
