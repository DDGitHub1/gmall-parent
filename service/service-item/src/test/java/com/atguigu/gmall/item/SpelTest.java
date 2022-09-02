package com.atguigu.gmall.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/1
 * @Description : com.atguigu.gmall.item
 * @Version : 1.0
 */
public class SpelTest {

    @Test
    public void test6() throws JsonProcessingException {
        String json = "[\"1\",\"a\"]"; //list<string>
        ObjectMapper mapper = new ObjectMapper();
        List<String> list = mapper.readValue(json, new TypeReference<List<String>>() {
        });
        System.out.println(list);
    }
    @Test
    public void test5(){
        hello hello = new hello();
        Method[] methods = hello.getClass().getMethods();
        for (Method method : methods) {
            System.out.println("method = " + method.getName()+ "返回值类型" + method.getGenericReturnType());
        }
    }


    @Test
    public void test3(){
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("new int[]{1,2,3}");
        int[] value = (int[]) expression.getValue();
        for (int i : value) {
            System.out.println("i = " + i);
        }


    }
    @Test
    public void test2(){
        Object[] params = new Object[]{1,2,3};
        Object[] params2 = new Object[]{2,2,3};
        Object[] params3 = new Object[]{3,2,3};
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("sku:info:#{#params[0]}", new TemplateParserContext());
        //准备一个计算上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //变量和上下文环境绑定  #代表上下文中取出一个变量
        context.setVariable("params",params2);
//        Object value = expression.getValue(context);
//        System.out.println("value = " + value);
        String value = expression.getValue(context, String.class);
        System.out.println(value);


    }

    @Test
    public void test(){
        //1.创建一个表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        String message = "Hello #{1+1}";
        Expression expression = parser.parseExpression(message,new TemplateParserContext());
        Object value = expression.getValue();
        System.out.println("value = " + value);
    }
}
