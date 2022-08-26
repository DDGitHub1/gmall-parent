package com.atguigu.gmall.product.service;

import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : dyh
 * @Date: 2022/8/25
 * @Description : com.atguigu.gmall.product.service
 * @Version : 1.0
 */
public interface FileUploadService{
    String upload(MultipartFile file) throws Exception;
}
