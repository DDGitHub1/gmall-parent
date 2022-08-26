package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : dyh
 * @Date: 2022/8/24
 * @Description : com.atguigu.gmall.product.controller
 * @Version : 1.0
 */
@RequestMapping("/admin/product")
@RestController
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;
    /**
     * Endpoint	对象存储服务的URL
     * Access Key	Access key就像用户ID，可以唯一标识你的账户。
     * Secret Key	Secret key是你账户的密码。
     * /**
     *      * 文件上传功能
     *      * 1、前端把文件流放到哪里了？我们该怎么拿到？
     *      *     Post请求数据在请求体（包含了文件[流]）
     *      * 如何接：
     *      * @RequestParam("file")MultipartFile file
     *      * @RequestPart("file")MultipartFile file: 专门处理文件的
     *      *
     *      * 各种注解接不通位置的请求数据
     *      * @RequestParam: 无论是什么请求 接请求参数； 用一个Pojo把所有数据都接了
     *      * @RequestPart： 接请求参数里面的文件项
     *      * @RequestBody： 接请求体中的所有数据 (json转为pojo)
     *      * @PathVariable: 接路径上的动态变量
     *      * @RequestHeader: 获取浏览器发送的请求的请求头中的某些值
     *      * @CookieValue： 获取浏览器发送的请求的Cookie值
     *      * - 如果多个就写数据，否则就写单个对象
     *      *
     *      *
     *      * @return
     *      */
    /**
     * 文件上传功能
     * @return
     */
    @PostMapping("/fileUpload")
    public Result fileupload(@RequestPart("file")MultipartFile file) throws Exception {
        //收到前端的文件流，上传给Minio。并返回这个文件在Minio中的存储地址。
        //以后用这个地址访问，数据库保存的也是这个地址
        String url = fileUploadService.upload(file);
        return Result.ok(url);
    }

}
