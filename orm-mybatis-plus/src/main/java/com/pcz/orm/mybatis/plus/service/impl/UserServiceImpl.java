package com.pcz.orm.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcz.orm.mybatis.plus.entity.User;
import com.pcz.orm.mybatis.plus.mapper.UserMapper;
import com.pcz.orm.mybatis.plus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author picongzhi
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
