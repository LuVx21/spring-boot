package org.luvx.boot.mul.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mul.mybatis.entity.User;
import org.luvx.boot.mul.mybatis.mapper.SelectMapper;
import org.luvx.boot.mul.mybatis.mapper.UpdateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    SelectMapper selectMapper;
    @Autowired
    UpdateMapper updateMapper;

    public void m1() {
        User user = selectMapper.selectByPrimaryKey(10000L);
        log.info("user:{}", user);
        int i = updateMapper.updateByPrimaryKey(user);
        log.info("update:{}", i);
    }
}
