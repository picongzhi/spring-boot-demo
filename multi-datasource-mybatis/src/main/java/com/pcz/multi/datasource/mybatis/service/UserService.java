package com.pcz.multi.datasource.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcz.multi.datasource.mybatis.model.User;

/**
 * @author picongzhi
 */
public interface UserService extends IService<User> {
    /**
     * 添加用户
     *
     * @param user 用户
     */
    void addUser(User user);
}
