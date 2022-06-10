package org.luvx.boot.es.repository;

import org.junit.jupiter.api.Test;
import org.luvx.boot.es.ApplicationTests;
import org.luvx.boot.es.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void m1() {
        for (int i = 0; i < 10000; i++) {
            User user = User.builder().userName("foo" + i).password("bar" + i).age(i).build();
            userRepository.save(user);
        }
    }
}