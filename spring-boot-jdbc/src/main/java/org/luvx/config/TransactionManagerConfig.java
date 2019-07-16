package org.luvx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @ClassName: org.luvx.config
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/7/16 15:29
 */
// @EnableTransactionManagement
public class TransactionManagerConfig {
    // @Bean
    // @Autowired
    // public PlatformTransactionManager txManager1(DataSource dataSource1) {
    //     return new DataSourceTransactionManager(dataSource1);
    // }
    //
    // @Bean
    // @Autowired
    // public PlatformTransactionManager txManager2(DataSource dataSource2) {
    //     return new DataSourceTransactionManager(dataSource2);
    // }
}
