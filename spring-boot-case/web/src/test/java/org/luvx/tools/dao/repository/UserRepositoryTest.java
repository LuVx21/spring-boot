package org.luvx.tools.dao.repository;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;
import org.luvx.boot.tools.dao.entity.User;
import org.luvx.boot.tools.dao.mapper.UserMapper;
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