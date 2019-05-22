package org.luvx.repository;

import org.luvx.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: org.luvx.repository
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/21 19:42
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
