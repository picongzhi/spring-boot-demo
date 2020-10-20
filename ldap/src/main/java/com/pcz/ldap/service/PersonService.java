package com.pcz.ldap.service;

import com.pcz.ldap.api.Result;
import com.pcz.ldap.entity.Person;
import com.pcz.ldap.request.LoginRequest;

/**
 * @author picongzhi
 */
public interface PersonService {
    /**
     * 登录
     *
     * @param request 请求参数
     * @return Result
     */
    Result login(LoginRequest request);

    /**
     * 查询所有
     *
     * @return Result
     */
    Result listAllPerson();

    /**
     * 保存
     *
     * @param person Person
     */
    void save(Person person);

    /**
     * 删除
     *
     * @param person Person
     */
    void delete(Person person);
}
