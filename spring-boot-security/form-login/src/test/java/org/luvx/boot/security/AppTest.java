package org.luvx.boot.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.boot.security.dao.UserDao;
import org.luvx.boot.security.entity.Role;
import org.luvx.boot.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.common.collect.Lists;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppTest {
    @Autowired
    UserDao userDao;

    @Test
    void contextLoads() {
        User u1 = new User();
        u1.setUsername("admin");
        u1.setPassword("1121");
        u1.setAccountNonExpired(true);
        u1.setAccountNonLocked(true);
        u1.setCredentialsNonExpired(true);
        u1.setEnabled(true);
        Role r1 = new Role();
        r1.setName("ROLE_admin");
        r1.setNameZh("管理员");
        u1.setRoles(Lists.newArrayList(r1));
        userDao.save(u1);
        User u2 = new User();
        u2.setUsername("user");
        u2.setPassword("1121");
        u2.setAccountNonExpired(true);
        u2.setAccountNonLocked(true);
        u2.setCredentialsNonExpired(true);
        u2.setEnabled(true);
        Role r2 = new Role();
        r2.setName("ROLE_user");
        r2.setNameZh("普通用户");
        u2.setRoles(Lists.newArrayList(r2));
        userDao.save(u2);
    }
}