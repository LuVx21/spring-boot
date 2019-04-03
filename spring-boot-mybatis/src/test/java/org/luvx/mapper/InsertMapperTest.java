package org.luvx.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
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
        userMapper.insert(user);
    }

    @Test
    public void insertSelectiveTest() {
        User user = User.builder()
                .userName("foo")
                .password("bar")
                .build();
        userMapper.insertSelective(user);
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

        userMapper.insertList(Arrays.asList(user1, user2));
    }

    @Test
    public void insertSelectiveListTest() {
    }
}