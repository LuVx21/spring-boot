package org.luvx.tools.dao.repository;

import org.junit.jupiter.api.Test;
import org.luvx.tools.BaseAppTests;
import org.luvx.tools.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class UserRepositoryTest extends BaseAppTests {
    @Autowired
    private UserRepository repository;

    @Test
    void m1() {
        Optional<User> byId = repository.findById(10000L);
        System.out.println(byId);
    }
}