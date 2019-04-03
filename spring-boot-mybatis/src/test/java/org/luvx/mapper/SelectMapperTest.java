package org.luvx.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SelectMapperTest {

    @Autowired
    private SelectMapper userMapper;

    @Test
    public void selectByPrimaryKeyTest() {
        User user = userMapper.selectByPrimaryKey(10043);
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
                .userName("foo")
                // .password("bar")
                // .age(19)
                .build();
        List<User> users = userMapper.selectSelectiveList(Arrays.asList(user1, user2));
        System.out.println(users);
    }

    /**
     * 条件查询
     */
    @Test
    public void selectSelective() {
        User user = new User();
        user.setSelectColumns("user_name, password, age");
        user.setUserName("foo");
        user.setWhereCon(" and id > 9999 ");
        user.setOrderCon(" order by id ");
        List<User> list = userMapper.selectSelective(user);
        System.out.println(list);
    }
}