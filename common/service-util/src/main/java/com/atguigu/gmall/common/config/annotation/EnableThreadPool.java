package com.atguigu.gmall.common.config.annotation;

import com.atguigu.gmall.common.config.threadpool.AppThreadPoolAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author : dyh
 * @Date: 2022/8/29
 * @Description : com.atguigu.gmall.common.config.annotation
 * @Version : 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AppThreadPoolAutoConfiguration.class)
//1、导入 AppThreadPoolAutoConfiguration 组件。
//2、开启 @EnableConfigurationProperties(AppThreadPoolProperties.class) 这个配置
//     - 和配置文件绑好
//     - AppThreadPoolProperties 放到容器
//3、AppThreadPoolAutoConfiguration 给容器中放一个 ThreadPoolExecutor
//效果： 随时 @Autowired ThreadPoolExecutor即可，也很方便改配置
public @interface EnableThreadPool {
}
