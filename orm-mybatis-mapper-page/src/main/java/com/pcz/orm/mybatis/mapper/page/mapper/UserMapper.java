package com.pcz.orm.mybatis.mapper.page.mapper;

import com.pcz.orm.mybatis.mapper.page.entity.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author picongzhi
 */
@Component
public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
}
