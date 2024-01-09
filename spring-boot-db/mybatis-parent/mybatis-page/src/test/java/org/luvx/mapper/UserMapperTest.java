package org.luvx.mapper;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;
import org.luvx.entity.User;
import org.luvx.entity.pojo.UserModel;
import org.springframework.beans.BeanUtils;

import jakarta.annotation.Resource;

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
        PageInfo<User> pageInfo = new PageInfo<>(users);
        System.out.println(JSON.toJSONString(pageInfo));
    }
}