package org.luvx.boot.mul.mybatis.service;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.mul.mybatis.entity.User;
import org.luvx.boot.mul.mybatis.mapper.read.SelectMapper;
import org.luvx.boot.mul.mybatis.mapper.write.UpdateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private SelectMapper selectMapper;
    @Autowired
    private UpdateMapper updateMapper;

    @Transactional
    // @Transactional("readTransactionManager")
    public void read() {
        User user = selectMapper.selectByPrimaryKey(10000L);
        log.info("user:{}", user);
    }

    @Transactional(rollbackFor = Exception.class)
    // @Transactional(transactionManager = "writeTransactionManager", rollbackFor = Exception.class)
    public void write() {
        User user = new User();
        user.setId(10000L);
        user.setUserName("10000");
        user.setPassword("write");
        user.setAge(30);
        int i = updateMapper.updateByPrimaryKey(user);
        // i = 0 / 0;
        log.info("update:{}", i);
    }
}
