package com.pcz.orm.jpa.repository;

import com.pcz.orm.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author picongzhi
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
}
