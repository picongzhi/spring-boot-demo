package com.pcz.elasticsearch.service;

import com.pcz.elasticsearch.model.Person;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author picongzhi
 */
public interface PersonService {
    /**
     * 创建索引
     *
     * @param index 索引
     */
    void createIndex(String index);

    /**
     * 删除索引
     *
     * @param index 索引
     */
    void deleteIndex(String index);

    /**
     * 插入数据
     *
     * @param index      索引
     * @param personList List<Person>
     */
    void insert(String index, List<Person> personList);

    /**
     * 更新数据
     *
     * @param index      索引
     * @param personList List<Person>
     */
    void update(String index, List<Person> personList);

    /**
     * 删除数据
     *
     * @param index  索引
     * @param person Person
     */
    void delete(String index, @Nullable Person person);

    /**
     * 查询
     *
     * @param index 索引
     * @return List<Person>
     */
    List<Person> searchList(String index);
}
