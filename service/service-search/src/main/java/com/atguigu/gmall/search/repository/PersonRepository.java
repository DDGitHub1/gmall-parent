package com.atguigu.gmall.search.repository;

import com.atguigu.gmall.search.bean.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : dyh
 * @Date: 2022/9/3
 * @Description : com.atguigu.gmall.search.repository
 * @Version : 1.0
 */
//PagingAndSortingRepository
@Repository  //@controller @service @component @Repository
public interface PersonRepository extends PagingAndSortingRepository<Person,Long> {
    List<Person> findAllByAddressLike(String address);
    List<Person> findAllByAgeLessThanEqual(Integer age);
    List<Person> findAllByAgeGreaterThanAndAddressLike(Integer age, String address);
    List<Person> findAllByAgeGreaterThanAndAddressLikeOrIdEquals(Integer age, String address, Long id);
}
