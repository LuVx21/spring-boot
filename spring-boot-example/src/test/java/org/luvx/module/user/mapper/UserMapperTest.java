package org.luvx.module.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.luvx.base.BaseTest;
import org.luvx.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: org.luvx.module.user.mapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/11 15:58
 */
public class UserMapperTest extends BaseTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void insertBatchTest() {
        User user1 = User.builder().userName("1111").password("1111").age(10).build();
        User user2 = User.builder().userName("1111").password("1111").age(10).build();
        User user3 = User.builder().userName("1111").password("1111").age(10).build();

        List<User> list = Arrays.asList(user1, user2, user3);
        userMapper.insertBatch(list);
    }

    @Test
    public void selectTest() {
        Page<User> page = new Page<>(0, 3);

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.gt("age", 0L);

        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
        System.out.println(iPage.getRecords());
    }
}