package com.atguigu.gmall.search;

import com.atguigu.gmall.search.bean.Person;
import com.atguigu.gmall.search.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @Author : dyh
 * @Date: 2022/9/3
 * @Description : com.atguigu.gmall.search
 * @Version : 1.0
 */
@SpringBootTest
public class EsTest {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Test
    public void test3(){
    }
    @Test
    public void test2(){
//        Optional<Person> byId = personRepository.findById(2L);
//        System.out.println(byId.get());
//        List<Person> 北京市 = personRepository.findAllByAddressLike("北京市");
//        System.out.println("北京市 = " + 北京市);
        List<Person> allage = personRepository.findAllByAgeLessThanEqual(18);
        System.out.println("allage = " + allage);
        List<Person> 上海市 = personRepository.findAllByAgeGreaterThanAndAddressLike(18, "上海市");
        System.out.println("上海市 = " + 上海市);
        List<Person> 北京市 = personRepository.findAllByAgeGreaterThanAndAddressLikeOrIdEquals(18, "北京市", 3L);
        System.out.println("北京市 = " + 北京市);

    }
    @Test
    public void test(){
        Person person1 = new Person();
        person1.setId(1L);
        person1.setFirstName("张1");
        person1.setLastName("三");
        person1.setAge(18);
        person1.setAddress("北京市丰台区");


        Person person = new Person();
        person.setId(2L);
        person.setFirstName("张");
        person.setLastName("三");
        person.setAge(19);
        person.setAddress("北京市朝阳区");

        Person person2 = new Person();
        person2.setId(3L);
        person2.setFirstName("李");
        person2.setLastName("三");
        person2.setAge(19);
        person2.setAddress("上海市松江区");

        Person person3 = new Person();
        person3.setId(4L);
        person3.setFirstName("张3");
        person3.setLastName("三");
        person3.setAge(20);
        person3.setAddress("北京市天安门");

        personRepository.save(person);
        personRepository.save(person2);
        personRepository.save(person3);
        personRepository.save(person1);
        System.out.println("完成");
    }
}
