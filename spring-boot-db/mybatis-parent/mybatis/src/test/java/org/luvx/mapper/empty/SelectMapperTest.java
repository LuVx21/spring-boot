package org.luvx.mapper.empty;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.common.entity.User;
import org.luvx.mapper.empty.SelectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
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

        user.setDistinctCon(true);
        user.setSelectColumns("user_name ");

        // user.setSelectColumns("user_name, group_concat(password) as password");
        // user.setGroupCon("group by user_name ");
        // user.setHavingCon("having count(1) > 1 ");

        user.setWhereCon("and id > 10043 ");
        user.setOrderCon(" order by id ");
        user.setLimitCon("limit 2 ");
        List<User> list = userMapper.selectSelective(user);
        System.out.println(list);
    }
}