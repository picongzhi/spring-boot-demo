package com.pcz.orm.beetlsql.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.pcz.orm.beetlsql.dao.UserDao;
import com.pcz.orm.beetlsql.entity.User;
import com.pcz.orm.beetlsql.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author picongzhi
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User saveUser(User user) {
        userDao.insert(user, true);
        return user;
    }

    @Override
    public void saveUserList(List<User> users) {
        userDao.insertBatch(users);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        if (ObjectUtil.isNull(user)) {
            throw new RuntimeException("用户id不能为null");
        }
        userDao.updateTemplateById(user);

        return userDao.single(user.getId());
    }

    @Override
    public User getUser(Long id) {
        return userDao.single(id);
    }

    @Override
    public List<User> getUserList() {
        return userDao.all();
    }

    @Override
    public PageQuery<User> getUserByPage(Integer currentPage, Integer pageSize) {
        return userDao.createLambdaQuery().page(currentPage, pageSize);
    }
}
