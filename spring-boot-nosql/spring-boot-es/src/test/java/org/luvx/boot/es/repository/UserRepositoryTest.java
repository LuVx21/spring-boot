package org.luvx.boot.es.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.luvx.boot.es.ApplicationTests;
import org.luvx.boot.es.entity.User;
import org.luvx.coding.common.more.MorePrints;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void m1() {
        for (int i = 2000; i < 4000; i++) {
            int i1 = i % 10;
            User user = User.builder()
                    .id(i + 1L)
                    .userName("xie_" + i1)
                    .password("ren_" + (10 - i1))
                    .age(i % 100)
                    .birthday(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    void m2() {
        userRepository.findById(80L)
                .ifPresent(MorePrints::println);
    }
}