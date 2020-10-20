package com.pcz.elasticsearch.repository;

import com.pcz.elasticsearch.model.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author picongzhi
 */
public interface PersonRepository extends ElasticsearchRepository<Person, Long> {
    /**
     * 根据年龄区间查询
     *
     * @param min 最小值
     * @param max 最大值
     * @return List<Person>
     */
    List<Person> findByAgeBetween(Integer min, Integer max);
}
