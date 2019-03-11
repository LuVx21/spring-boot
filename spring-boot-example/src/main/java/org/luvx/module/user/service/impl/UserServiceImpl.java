package org.luvx.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.luvx.common.annotation.MeasurementAnnotation;
import org.luvx.module.user.entity.User;
import org.luvx.module.user.mapper.UserMapper;
import org.luvx.module.user.model.UserModel;
import org.luvx.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: org.luvx.module.user.service.impl
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/8 17:28
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int insertUser(User user) {
        userMapper.insert(user);
        return user.getUserId().intValue();
    }

    @Override
    public int deleteUserById(Long id) {
        int num = userMapper.deleteById(id);
        return num;
    }

    @Override
    public int updateUser(Long id, User user) {
        user.setUserId(id);
        int num = userMapper.updateById(user);
        return num;
    }

    @MeasurementAnnotation
    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public IPage<User> selectUsers(UserModel userModel) {
        Page<User> page = new Page<>(userModel.getCurrentPage(), userModel.getSize());

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.gt("age", 0L);

        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
        return iPage;
    }
}
