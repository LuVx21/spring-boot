package org.luvx.service.impl;

import org.luvx.entity.User;
import org.luvx.repository.UserRepository;
import org.luvx.service.UserService;
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
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/7/16 15:45
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository             userRepository;
    @Autowired
    private JdbcTemplate               jdbcTemplate;
    @Autowired
    private TransactionTemplate        transactionTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public void method() {
        transactionTemplate.execute((TransactionStatus status) -> {
            try {
                /// jdbcTemplate.update("update user set age = 100 where id = 10000;");
                User user = User.builder().id(10000L).userName("10000").password("10000").age(100).build();
                userRepository.saveAndFlush(user);
                // int i = 2 / 0;
            } catch (Exception e) {
                status.setRollbackOnly();
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public void method1() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            /// jdbcTemplate.update("update user set age = 100 where id = 10000;");
            User user = User.builder().id(10000L).userName("10000").password("100000").age(100).build();
            userRepository.saveAndFlush(user);
            // int i = 2 / 0;
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void method2() {
        /// jdbcTemplate.update("update user set age = 100 where id = 10000;");
        User user = User.builder().id(10000L).userName("10000").password("10000").age(100).build();
        userRepository.saveAndFlush(user);
        // int i = 2 / 0;
    }
}
