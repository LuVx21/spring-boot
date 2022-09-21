package org.luvx.tools.dao.repository;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;
import org.luvx.tools.dao.user.entity.User;
import org.luvx.tools.dao.user.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class UserRepositoryTest extends BaseAppTests {
    @Autowired
    private UserMapper mapper;

    @Test
    void m1() {
        Optional<User> byId = mapper.selectByPrimaryKey(10000L);
        System.out.println(byId);
    }
}