package org.luvx.repository;

import org.luvx.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: org.luvx.repository
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/7/16 15:49
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}