package org.luvx.anno.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Resource(name = "annoUserMapper")
    private UserMapper userMapper;

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

    @Test
    public void updateByPrimaryKeyTest() {
        User user = User.builder()
                .id(10043L)
                .userName("foo")
                .password("bar")
                .age(19)
                .build();

        userMapper.updateByPrimaryKey(user);
    }

    @Test
    public void updateByPrimaryKeySelectiveTest() {
        User user = User.builder()
                .id(10044L)
                .userName("foo")
                .password("bar")
                .build();

        userMapper.updateByPrimaryKeySelective(user);
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

    @Test
    public void selectByPrimaryKeyTest() {
        User user = userMapper.selectByPrimaryKey(1L);
        System.out.println(user);
    }

    @Test
    public void selectSelectiveTest() {
        User user = User.builder()
                .userName("foo")
                .password("bar")
                .age(18)
                .build();
        List<User> users = userMapper.selectSelective(user);
        System.out.println(users);
    }

    @Test
    public void selectByPrimaryKeyListTest() {
        List<User> users = userMapper.selectByPrimaryKeyList(Arrays.asList(10043, 10044));
        System.out.println(users);
    }

    @Test
    public void selectSelectiveListTest() {
        User user1 = User.builder()
                .userName("foo")
                .password("bar")
                .age(18)
                .build();
        User user2 = User.builder()
                .userName("foo_")
                // .password("bar")
                .age(20)
                .build();
        List<User> users = userMapper.selectSelectiveList(Arrays.asList(user1, user2));
        System.out.println(users);
    }
}
