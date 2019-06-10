package org.luvx.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @ClassName: org.luvx.mapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 17:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void insertTest() {
        User user = User.builder()
                // .userId(10010L)
                .userName("userName")
                .password("password")
                .age(10)
                .build();
        User user1 = User.builder()
                // .userId(10010L)
                .userName("userName1")
                .password("password1")
                .age(11)
                .build();
        // userMapper.insert(user);
        // userMapper.insertSelective(user);
        userMapper.insertList(Arrays.asList(user, user1));
    }

    @Test
    public void deleteTest() {
        // userMapper.deleteByPrimaryKey(10021L);
        // userMapper.deleteByPrimaryKeyList(Arrays.asList(10021L, 10022L));
        User user = User.builder()
                .userName("userName")
                .password("password")
                .age(10)
                .build();
        User user1 = User.builder()
                .userName("userName1")
                .password("password1")
                .age(100)
                .build();
        // userMapper.deleteSelective(user);
        // userMapper.deleteSelectiveList(Arrays.asList(user, user1));
    }

    @Test
    public void updateTest() {
        User user0 = User.builder()
                .userId(10055L)
                .build();
        User user = User.builder()
                .userId(10055L)
                .userName("userName1")
                .password("password1")
                // .age(101)
                .build();
        // userMapper.updateByPrimaryKey(user);
        // userMapper.updateSelective(user0, user);
        // userMapper.updateByPrimaryKeyList(Arrays.asList(10055L, 10056L), user);
        userMapper.updateSelectiveList(Arrays.asList(user0, user0), user);
    }

    @Test
    public void selectTest() {
        User user = userMapper.selectByPrimaryKey(10043L);
        System.out.println(user);
    }
}