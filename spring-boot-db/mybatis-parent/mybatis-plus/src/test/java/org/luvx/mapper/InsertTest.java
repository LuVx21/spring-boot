package org.luvx.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.luvx.enums.BizTypeEnum;
import org.luvx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

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
                // .id(1L)
                .userName("foo")
                .password("bar")
                .age(18)
                .updateTime(LocalDateTime.now())
                .bizType(BizTypeEnum.B)
                .build();

        userMapper.insert(user);
        log.info("after insert:{}", user);
    }
}