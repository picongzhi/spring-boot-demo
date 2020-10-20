package com.pcz.orm.jdbc.template.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.pcz.orm.jdbc.template.constant.Const;
import com.pcz.orm.jdbc.template.dao.UserDao;
import com.pcz.orm.jdbc.template.entity.User;
import com.pcz.orm.jdbc.template.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author picongzhi
 */
@Service
public class UserServiceImpl implements IUserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Boolean save(User user) {
        String rowPass = user.getPassword();
        String salt = IdUtil.simpleUUID();
        String pass = SecureUtil.md5(rowPass + Const.SALT_PREFIX + salt);
        user.setPassword(pass);
        user.setSalt(salt);

        return userDao.insert(user) > 0;
    }

    @Override
    public Boolean delete(Long id) {
        return userDao.delete(id) > 0;
    }

    @Override
    public Boolean update(User user, Long id) {
        User exist = getUser(id);
        if (StrUtil.isNotBlank(user.getPassword())) {
            String rowPass = user.getPassword();
            String salt = IdUtil.simpleUUID();
            String pass = SecureUtil.md5(rowPass + Const.SALT_PREFIX + salt);
            user.setPassword(pass);
            user.setSalt(salt);
        }
        BeanUtil.copyProperties(user, exist, CopyOptions.create().setIgnoreNullValue(true));
        exist.setLastUpdateTime(new DateTime());

        return userDao.update(exist, id) > 0;
    }

    @Override
    public User getUser(Long id) {
        return userDao.findOneById(id);
    }

    @Override
    public List<User> getUser(User user) {
        return userDao.findByExample(user);
    }
}
