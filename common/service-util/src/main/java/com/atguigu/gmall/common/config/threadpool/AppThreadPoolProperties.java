package com.atguigu.gmall.common.config.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author : dyh
 * @Date: 2022/8/28
 * @Description : com.atguigu.gmall.item.config
 * @Version : 1.0
 */
@ConfigurationProperties(prefix = "app.thread-pool")
@Component
@Data
public class AppThreadPoolProperties {
    Integer core = 2;
    Integer max = 4;
    Integer queueSize = 200;
    Long keepAliveTime = 300L;
}
