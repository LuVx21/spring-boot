package org.luvx.boot.mul.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mul.mybatis.common.ConstValue;
import org.luvx.boot.mul.mybatis.entity.User;
import org.luvx.boot.mul.mybatis.mapper.read.SelectMapper;
import org.luvx.boot.mul.mybatis.mapper.write.UpdateMapper;
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
    // @Transactional(ConstValue.readTransactionManager)
    public void read() {
        User user = new User();
        user.setId(10000L);
        user.setUserName("read");
        user.setPassword(LocalDateTime.now().toString());
        user.setAge(18);
        int i = selectMapper.updateByPrimaryKey(user);
        // i = 0 / 0;
        User user1 = selectMapper.selectByPrimaryKey(10000L);
        log.info("update:{} user:{}", i, user1);
    }

    @Transactional
    // @Transactional(ConstValue.writeTransactionManager)
    public void write() {
        User user = new User();
        user.setId(10000L);
        user.setUserName("write");
        user.setPassword(LocalDateTime.now().toString());
        user.setAge(30);
        int i = updateMapper.updateByPrimaryKey(user);
        // i = 0 / 0;
        User user1 = updateMapper.selectByPrimaryKey(10000L);
        log.info("update:{}", i, user1);
    }
}
