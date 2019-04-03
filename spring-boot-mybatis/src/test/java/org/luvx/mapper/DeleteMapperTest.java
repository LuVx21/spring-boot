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
public class DeleteMapperTest {

    @Autowired
    private DeleteMapper userMapper;

    @Test
    public void deleteByPrimaryKeyTest() {
        userMapper.deleteByPrimaryKey(10037L);
    }

    @Test
    public void deleteSelectiveTest() {
        User user = User.builder()
                .userName("foo")
                .password("bar")
                .age(18)
                .build();
        userMapper.deleteSelective(user);
    }

    @Test
    public void deleteByPrimaryKeyListTest() {
        userMapper.deleteByPrimaryKeyList(Arrays.asList(10038, 10039));
    }

    @Test
    public void deleteSelectiveListTest() {
        User user1 = User.builder()
                .userName("foo")
                .password("bar")
                .age(18)
                .build();
        User user2 = User.builder()
                .userName("foo")
                .password("bar")
                .age(19)
                .build();

        userMapper.deleteSelectiveList(Arrays.asList(user1, user2));
    }
}