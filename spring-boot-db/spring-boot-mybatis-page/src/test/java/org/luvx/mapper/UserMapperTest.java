package org.luvx.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.luvx.entity.pojo.UserModel;
import org.springframework.beans.BeanUtils;

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
        UserModel build = UserModel.builder()
                .userName("foo")
                .page(new Page<>(3, 4))
                .build();
        User record = new User();
        BeanUtils.copyProperties(build, record);

        PageHelper.startPage(build.getPage().getPageNum(), build.getPage().getPageSize());
        Page<User> users = userMapper.selectSelective(record);
        System.out.println(users);
    }
}