package com.pcz.ldap.repository;

import com.pcz.ldap.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.naming.Name;

/**
 * @author picongzhi
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Name> {
    /**
     * 根据用户名查找
     *
     * @param uid 用户名
     * @return Person
     */
    Person findByUid(String uid);
}
