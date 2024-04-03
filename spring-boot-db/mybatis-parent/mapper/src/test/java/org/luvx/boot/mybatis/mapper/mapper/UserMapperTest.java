package org.luvx.boot.mybatis.mapper.mapper;

import org.junit.jupiter.api.Test;
import org.luvx.boot.mybatis.mapper.MapperAppTests;
import org.luvx.boot.mybatis.mapper.common.base.QueryEntity;
import org.luvx.boot.mybatis.mapper.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

class UserMapperTest extends MapperAppTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void insertTest() {
        User user = new User()
                // .setId(10010L)
                .setUserName("userName")
                .setPassword("password")
                .setAge(10)
                // .setBirthday(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        User user1 = new User()
                // .setId(10010L)
                .setUserName("userName1")
                .setPassword("password1")
                .setAge(11)
                .setBirthday(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        // pass
        // userMapper.insert(user);
        // pass
        // userMapper.insertList(List.of(user, user1));
        // pass
        // userMapper.insertSelective(user);
        // ignore
        // userMapper.insertSelectiveList(List.of(user, user1));
    }

    @Test
    void deleteTest() {
        // userMapper.deleteByPrimaryKey(10021L);
        // userMapper.deleteByPrimaryKeyList(List.of(10021L, 10022L));
        User user = new User()
                .setUserName("userName")
                .setPassword("password")
                .setAge(10);
        User user1 = new User()
                .setUserName("userName1")
                .setPassword("password1")
                .setAge(11);
        // userMapper.deleteSelective(user);
        // userMapper.deleteSelectiveList(List.of(user, user1));
    }

    @Test
    void updateTest() {
        User user = new User()
                .setId(10021L)
                .setUserName("userName1")
                .setPassword("password1")
                .setAge(101);
        // pass
        // userMapper.updateByPrimaryKey(user);
        // pass
        // userMapper.updateByPrimaryKeyList(List.of(10020L, 10021L), user);
        // pass
        // userMapper.updateByPrimaryKeySelective(user);
        // pass
        // userMapper.updateSelective(new User().setUserName("userName"), user);
        // pass
        // userMapper.updateSelectiveList(List.of(new User().setUserName("a"), new User().setUserName("b")), user);
    }

    @Test
    void selectTest() {
        final long id = 10000L;

        User user = userMapper.selectByPrimaryKey(id);
        System.out.println(user);

        User record = new User().setId(id).setUserName("foo");
        List<User> userList = userMapper.selectSelective(record);
        System.out.println(userList);

        userList = userMapper.selectBatchIds(List.of(1L, id));
        System.out.println(userList);

        userList = userMapper.selectSelectiveList(List.of(new User().setUserName("foo1"), record));
        System.out.println(userList);
    }

    @Test
    void selectTest1() {
        QueryEntity record = new QueryEntity();
        record.setSelectColumns("id, user_name, password,age")
                .setWhereCon("id = 1");
        List<User> select = userMapper.selectCustomer(record);
        System.out.println(select);
    }
}