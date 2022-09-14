package com.atguigu.gmall.order;

import com.atguigu.gmall.feign.ware.WareFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.PublicKey;

/**
 * @Author : dyh
 * @Date: 2022/9/13
 * @Description : com.atguigu.gmall.order
 * @Version : 1.0
 */
@SpringBootTest
public class FeignTest {
    @Autowired
    WareFeignClient wareFeignClient;
    @Test
    public void test(){
//        String search = wareFeignClient.search("尚硅谷");
//        System.out.println("search = " + search);

        String hasStock = wareFeignClient.hasStock(43L, 2);
        System.out.println("hasStock = " + hasStock);
    }
}
