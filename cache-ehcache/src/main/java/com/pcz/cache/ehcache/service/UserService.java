package com.pcz.cache.ehcache.service;

import com.pcz.cache.ehcache.entity.User;

/**
 * @author picongzhi
 */
public interface UserService {
    /**
     * 保存或修改用户
     *
     * @param user 用户
     * @return User
     */
    User saveOrUpdate(User user);

    /**
     * 获取用户
     *
     * @param id 主键id
     * @return User
     */
    User get(Long id);

    /**
     * 删除
     *
     * @param id 主键id
     */
    void delete(Long id);
}
