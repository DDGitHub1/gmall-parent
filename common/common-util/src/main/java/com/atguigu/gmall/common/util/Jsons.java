package com.atguigu.gmall.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @Author : dyh
 * @Date: 2022/8/28
 * @Description : com.atguigu.gmall.common.util
 * @Version : 1.0
 */
public class Jsons {
    private static ObjectMapper mapper = new ObjectMapper();
    /**
     * 对象转为json字符串
     * @param object
     * @return
     */
    public static String toStr(Object object) {

        try {
            String s = mapper.writeValueAsString(object);
            return s;
        } catch (JsonProcessingException e) {
           return null;
        }
    }
}
