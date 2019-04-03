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
public class UpdateMapperTest {

    @Autowired
    private UpdateMapper userMapper;

    @Test
    public void updateByPrimaryKeyTest() {
        User user = User.builder()
                .userName("foo")
                .password("bar")
                .age(19)
                .build();

        userMapper.updateByPrimaryKey(10043, user);
    }

    @Test
    public void updateSelectiveTest() {
        User user = User.builder()
                .userName("foo")
                .password("bar")
                .age(18)
                .build();
        User user1 = User.builder()
                .userName("foo_")
                .password("bar_")
                .age(20)
                .build();
        userMapper.updateSelective(user, user1);
    }

    @Test
    public void updateByPrimaryKeyListTest() {
        User user = User.builder()
                .userName("foo")
                .password("bar")
                .age(20)
                .build();

        userMapper.updateByPrimaryKeyList(Arrays.asList(10043, 10044), user);
    }

    @Test
    public void updateSelectiveListTest() {
        User user1 = User.builder()
                .userName("foo")
                .password("bar")
                .age(20)
                .build();
        User user2 = User.builder()
                .userName("foo")
                .password("bar")
                .age(21)
                .build();

        User user3 = User.builder()
                .userName("foo_")
                .password("bar_")
                .age(18)
                .build();
        userMapper.updateSelectiveList(Arrays.asList(user1, user2), user3);
    }
}