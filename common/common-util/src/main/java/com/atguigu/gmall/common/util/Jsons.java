package com.atguigu.gmall.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

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

    public static<T>  T toObj(String jsonStr, Class<T> clz) {
        if(StringUtils.isEmpty(jsonStr)){
            return null;
        }
        T t = null;
        try {
            t = mapper.readValue(jsonStr, clz);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
