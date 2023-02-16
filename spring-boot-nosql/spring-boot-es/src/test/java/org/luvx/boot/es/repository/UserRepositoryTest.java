package org.luvx.boot.es.repository;

import java.time.LocalDateTime;
import java.util.stream.Stream;

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
        for (int i = 100; i < 4000; i++) {
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
        User user1 = User.builder().id(1L).userName("张三").password("foobar").remark("张三").build();
        User user2 = User.builder().id(2L).userName("张三丰").password("foobar").remark("张三丰").build();
        User user3 = User.builder().id(3L).userName("张飞").password("foobar").remark("张飞").build();
        User user4 = User.builder().id(4L).userName("三德子").password("foobar").remark("三德子").build();
        User user5 = User.builder().id(5L).userName("张二丰").password("foobar").remark("张二丰").build();
        User user6 = User.builder().id(6L).userName("孙权").password("foobar").remark("孙权").build();
        User user7 = User.builder().id(7L).userName("马三丰").password("foobar").remark("马三丰").build();
        Stream.of(user1, user2, user3, user4, user5, user6, user7)
                .forEachOrdered(userRepository::save);
    }

    @Test
    void m3() {
        userRepository.findById(80L)
                .ifPresent(MorePrints::println);
    }
}