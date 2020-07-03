package org.luvx.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.json.JSONArray;
import org.junit.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;

import javax.annotation.Resource;

public class UserMapperTest extends ApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void getTest() {
        PageHelper.startPage(3, 4);
        Page<User> userList = userMapper.getUserList();
        System.out.println(userList);
    }

    @Test
    public void getTest1() {
        PageHelper.startPage(3, 4);
        User record = new User();
        record.setAge(25);
        Page<User> users = userMapper.selectSelective(record);
        System.out.println(users);
    }
}