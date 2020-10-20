package com.pcz.ldap.service.impl;

import com.pcz.ldap.api.Result;
import com.pcz.ldap.entity.Person;
import com.pcz.ldap.exception.ServiceException;
import com.pcz.ldap.repository.PersonRepository;
import com.pcz.ldap.request.LoginRequest;
import com.pcz.ldap.service.PersonService;
import com.pcz.ldap.util.LdapUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.NoSuchAlgorithmException;

/**
 * @author picongzhi
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public Result login(LoginRequest request) {
        log.info("IN LDAP auth");

        Person person = personRepository.findByUid(request.getUsername());
        try {
            if (ObjectUtils.isEmpty(person)) {
                throw new SecurityException("用户名或密码错误，请重新尝试");
            } else {
                person.setUserPassword(LdapUtils.asciiToString(person.getUserPassword()));
                if (!LdapUtils.verify(person.getUserPassword(), request.getPassword())) {
                    throw new ServiceException("用户名或密码错误，请重新尝试");
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        log.info("person info: {}", person);

        return Result.success(person);
    }

    @Override
    public Result listAllPerson() {
        Iterable<Person> people = personRepository.findAll();
        people.forEach(person -> person.setUserPassword(LdapUtils.asciiToString(person.getUserPassword())));

        return Result.success(people);
    }

    @Override
    public void save(Person person) {
        Person p = personRepository.save(person);
        log.info("用户{}保存成功", p.getUid());
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
        log.info("删除用户{}成功", person);
    }
}
