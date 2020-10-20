package com.pcz.orm.jdbc.template.service;

import com.pcz.orm.jdbc.template.entity.User;

import java.util.List;

/**
 * @author picongzhi
 */
public interface IUserService {
    /**
     * 保存用户
     *
     * @param user 用户
     * @return 结果
     */
    Boolean save(User user);

    /**
     * 删除用户
     *
     * @param id 主键
     * @return 结果
     */
    Boolean delete(Long id);

    /**
     * 更新用户
     *
     * @param user 用户
     * @param id   主键
     * @return 结果
     */
    Boolean update(User user, Long id);

    /**
     * 获取单个用户
     *
     * @param id 主键
     * @return 用户
     */
    User getUser(Long id);

    /**
     * 获取用户列表
     *
     * @param user 用户
     * @return 用户列表
     */
    List<User> getUser(User user);
}
