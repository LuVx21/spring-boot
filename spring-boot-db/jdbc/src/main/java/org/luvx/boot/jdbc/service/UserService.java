package org.luvx.boot.jdbc.service;

import org.luvx.boot.jdbc.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @ClassName: org.luvx.service.impl
 * @Description: 编程式事务和声明式事务
 * @Author: Ren, Xie
 * @Date: 2019/7/16 15:45
 */
@Service
public class UserService {
    @Autowired
    private JdbcTemplate               jdbcTemplate;
    @Autowired
    private TransactionTemplate        transactionTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Transactional(rollbackFor = Exception.class)
    public void method0() {
        jdbcTemplate.update("update user set user_name = '10000' where id = 10000;");
        // int i = 2 / 0;
    }

    public void method1() {
        transactionTemplate.execute((TransactionStatus status) -> {
            int cnt = 0;
            try {
                cnt = jdbcTemplate.update("update user set password = '10000' where id = 10000;");
                // int i = 2 / 0;
            } catch (Exception e) {
                status.setRollbackOnly();
                e.printStackTrace();
            }
            return cnt;
        });
    }

    public void method2() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            jdbcTemplate.update("update user set age = 100 where id = 10000;");
            // int i = 2 / 0;
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            e.printStackTrace();
        }
    }

    @Autowired
    TransactionUtils utils;

    public void method3() {
        Integer transact = utils.transact(
                (Object obj) -> jdbcTemplate.update("update user set age = 101 where id = 10000;"),
                ""
        );
    }
}
