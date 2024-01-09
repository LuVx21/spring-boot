package org.luvx.mapper.empty;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.common.entity.User;
import org.luvx.mapper.empty.InsertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class InsertMapperTest {

    @Autowired
    private InsertMapper userMapper;

    @Test
    public void insertTest() {
        User user = User.builder()
                .userName("foo")
                .password("bar")
                .age(18)
                .build();
        log.info("插入前: {}", user);
        userMapper.insert(user);
        log.info("插入后: {}", user);
    }

    @Test
    public void insertSelectiveTest() {
        User user = User.builder()
                .userName("foo")
                .password("bar")
                .build();
        log.info("插入前: {}", user);
        userMapper.insertSelective(user);
        log.info("插入后: {}", user);
    }

    @Test
    public void insertListTest() {
        User user1 = User.builder()
                .userName("foo")
                .password("bar")
                .age(18)
                .build();
        User user2 = User.builder()
                .userName("foo")
                .password("bar")
                .age(18)
                .build();
        log.info("插入前user1: {}", user1);
        log.info("插入前user2: {}", user1);
        userMapper.insertList(Arrays.asList(user1, user2));
        log.info("插入后user1: {}", user1);
        log.info("插入后user2: {}", user2);
    }

    @Test
    public void insertSelectiveListTest() {
    }
}