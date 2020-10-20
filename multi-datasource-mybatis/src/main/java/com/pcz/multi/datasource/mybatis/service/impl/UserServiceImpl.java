package com.pcz.multi.datasource.mybatis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcz.multi.datasource.mybatis.mapper.UserMapper;
import com.pcz.multi.datasource.mybatis.model.User;
import com.pcz.multi.datasource.mybatis.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author picongzhi
 */
@Service
@DS("slave")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 在方法上写DS("master")默认主库
     *
     * @param user 用户
     */
    @DS("master")
    @Override
    public void addUser(User user) {
        baseMapper.insert(user);
    }
}
