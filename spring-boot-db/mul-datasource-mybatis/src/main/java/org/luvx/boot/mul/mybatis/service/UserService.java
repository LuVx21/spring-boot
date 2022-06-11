package org.luvx.boot.mul.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mul.mybatis.common.ConstValue;
import org.luvx.boot.mul.mybatis.entity.User;
import org.luvx.boot.mul.mybatis.mapper.ds1.UpdateMapper;
import org.luvx.boot.mul.mybatis.mapper.ds2.SelectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserService {
    @Autowired
    private SelectMapper selectMapper;
    @Autowired
    private UpdateMapper updateMapper;

    @Transactional
    // @Transactional(ConstValue.ds1TransactionManager)
    public void ds1Operate() {
        User user = new User();
        user.setId(10000L);
        user.setUserName("ds1");
        user.setPassword(LocalDateTime.now().toString());
        user.setAge(30);
        int i = updateMapper.updateByPrimaryKey(user);
        // i = 0 / 0;
        User user1 = updateMapper.selectByPrimaryKey(10000L);
        log.info("update:{} user:{}", i, user1);
    }

    @Transactional
    // @Transactional(ConstValue.ds2TransactionManager)
    public void ds2Operate() {
        User user = new User();
        user.setId(10000L);
        user.setUserName("ds2");
        user.setPassword(LocalDateTime.now().toString());
        user.setAge(18);
        int i = selectMapper.updateByPrimaryKey(user);
        // i = 0 / 0;
        User user1 = selectMapper.selectByPrimaryKey(10000L);
        log.info("update:{} user:{}", i, user1);
    }
}
