package com.pcz.ldap;

import com.pcz.ldap.api.Result;
import com.pcz.ldap.entity.Person;
import com.pcz.ldap.request.LoginRequest;
import com.pcz.ldap.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootLdapApplicationTest {
    @Resource
    private PersonService personService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void loginTest() {
        LoginRequest loginRequest = LoginRequest.builder().username("admin").password("admin").build();
        Result result = personService.login(loginRequest);
        log.info("result: {}", result);
    }

    @Test
    public void saveTest() {
        Person person = new Person();
        person.setUid("picongzhi");
        person.setSurname("pi");
        person.setGidNumber("congzhi");
        person.setUserPassword("123456");

        person.setPersonName("picongzhi");
        person.setUidNumber("20");
        person.setGidNumber("20");
        person.setHomeDirectory("/home/picongzhi");
        person.setLoginShell("/bin/zsh");

        personService.save(person);
    }
}
