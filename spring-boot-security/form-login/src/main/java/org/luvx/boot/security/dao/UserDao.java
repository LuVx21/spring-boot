package org.luvx.boot.security.dao;

import org.luvx.boot.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
