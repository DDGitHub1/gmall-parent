package com.atguigu.gmall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @Author : dyh
 * @Date: 2022/9/3
 * @Description : com.atguigu.gmall.search
 * @Version : 1.0
 */

/**
 *  ElasticsearchDataAutoConfiguration;
 *  ElasticsearchRepositoriesAutoConfiguration;
 */
@EnableElasticsearchRepositories
@SpringCloudApplication
public class SearchMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchMainApplication.class,args);
    }
}
