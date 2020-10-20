package com.pcz.orm.beetlsql.dao;

import com.pcz.orm.beetlsql.entity.User;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * @author picongzhi
 */
@Component
public interface UserDao extends BaseMapper<User> {
}
