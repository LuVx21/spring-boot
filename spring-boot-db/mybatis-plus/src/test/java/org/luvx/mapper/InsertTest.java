package org.luvx.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.luvx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Slf4j
class InsertTest extends ApplicationTests {
    @Autowired
    private UserMapper  userMapper;
    @Autowired
    private UserService userService;

    @Test
    void m1() {
        // userService.save();
        List<User> of = List.of();
        userService.saveBatch(of);
    }

    @Test
    void insertTest() {
        User user = User.builder()
                // .userId(1L)
                .userName("xie")
                .password("ren")
                .age(18)
                .build();

        userMapper.insert(user);
        log.info("after insert:{}", user);
    }
}