package org.luvx.boot.mul.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mul.mybatis.entity.User;
import org.luvx.boot.mul.mybatis.mapper.SelectMapper;
import org.luvx.boot.mul.mybatis.mapper.UpdateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {
    @Autowired
    SelectMapper selectMapper;
    @Autowired
    UpdateMapper updateMapper;

    // @Transactional
    public void read() {
        User user = selectMapper.selectByPrimaryKey(10000L);
        log.info("user:{}", user);
    }

    // @Transactional
    public void write() {
        User user = new User();
        user.setId(10000L);
        user.setUserName("10000");
        user.setPassword("hahaha");
        user.setAge(30);
        int i = updateMapper.updateByPrimaryKey(user);
        log.info("update:{}", i);
    }
}
