package com.pcz.orm.mybatis.mapper;

import com.pcz.orm.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author picongzhi
 */
@Mapper
@Component
public interface UserMapper {
    /**
     * 查询所有用户
     *
     * @return List<User>
     */
    @Select("SELECT * FROM orm_user")
    List<User> selectAllUser();

    /**
     * 根据id查询用户
     *
     * @param id 主键
     * @return User
     */
    @Select("SELECT * FROM orm_user WHERE id = #{id}")
    User selectUserById(@Param("id") Long id);

    /**
     * 保存用户
     *
     * @param user 用户
     * @return 成功：1，失败：0
     */
    int saveUser(@Param("user") User user);

    /**
     * 删除用户
     *
     * @param id 主键
     * @return 成功：1，失败：0
     */
    int deleteById(@Param("id") Long id);
}
