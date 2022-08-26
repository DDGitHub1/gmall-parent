package com.atguigu.gmall.product;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author : dyh
 * @Date: 2022/8/25
 * @Description : com.atguigu.gmall.product
 * @Version : 1.0
 */
public class MinioTest {
    /**
     * Endpoint	对象存储服务的URL
     * Access Key	Access key就像用户ID，可以唯一标识你的账户。
     * Secret Key	Secret key是你账户的密码。
     */

    @Test
    public void uploadTest() throws Exception {
        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient("http://192.168.6.100:9000", "admin", "admin123456");

            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists("gmall");
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket("gmall");
            }
            //"D:\220310\雷神\资料3\03 商品图片\品牌\pingguo.png"
            //String bucketName, String objectName, InputStream stream, PutObjectOptions options
            // 桶名  对象名  文件流  上传的参数设置
            FileInputStream inputStream = new FileInputStream("D:\\220310\\雷神\\资料3\\03 商品图片\\品牌\\pingguo.png");
            minioClient.putObject("gmall","pingguo.png",inputStream,new PutObjectOptions(inputStream.available(),-1L));
            PutObjectOptions options = new PutObjectOptions(inputStream.available(), -1L);
            // 使用putObject上传一个文件到存储桶中。
            options.setContentType("image/png");
            //告诉Minio上传的这个文件的内容类型
            minioClient.putObject("gmall",
                    "pingguo.png",
                    inputStream,
                    options
            );
            System.out.println("上传成功");
        } catch(MinioException e) {
            System.out.println("上传失败" + e);
        }
    }
}
