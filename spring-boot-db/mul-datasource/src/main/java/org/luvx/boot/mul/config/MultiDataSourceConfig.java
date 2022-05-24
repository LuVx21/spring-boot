package org.luvx.boot.mul.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Slf4j
@Configuration
public class MultiDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource0")
    public DataSourceProperties dataSourceConf0() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource1")
    public DataSourceProperties dataSourceConf1() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource0() {
        DataSourceProperties dataSourceProperties = dataSourceConf0();
        log.info("datasource0: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public DataSource dataSource1() {
        DataSourceProperties dataSourceProperties = dataSourceConf1();
        log.info("datasource1: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager txManager0(DataSource dataSource0) {
        return new DataSourceTransactionManager(dataSource0);
    }

    @Bean
    @Resource
    public PlatformTransactionManager txManager1(DataSource dataSource1) {
        return new DataSourceTransactionManager(dataSource1);
    }

    @Bean("dataSource0Template")
    public JdbcTemplate storyJdbcTemplate(DataSource dataSource0) {
        return new JdbcTemplate(dataSource0);
    }

    @Bean("dataSource1Template")
    public JdbcTemplate testJdbcTemplate(DataSource dataSource1) {
        return new JdbcTemplate(dataSource1);
    }
}
