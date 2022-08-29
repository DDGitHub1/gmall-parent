package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author : dyh
 * @Date: 2022/8/29
 * @Description : com.atguigu.gmall.item.controller
 * @Version : 1.0
 */
@RestController
public class ThreadPoolController {
    @Autowired
    ThreadPoolExecutor executor;

    @GetMapping("/close/pool")
    public Result closePool(){
        executor.shutdown(); //关闭线程池
        return Result.ok();
    }

    @GetMapping("/monitor/pool")
    public Result monitorThreadPool(){

        int poolSize = executor.getCorePoolSize();
        long taskCount = executor.getTaskCount();

        return Result.ok(poolSize + "====" + taskCount);
    }
}
