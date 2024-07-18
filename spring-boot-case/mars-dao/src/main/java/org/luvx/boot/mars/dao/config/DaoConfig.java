package org.luvx.boot.mars.dao.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
@ComponentScan({"org.luvx.boot.mars.dao"})
@MapperScan({"org.luvx.boot.mars.dao.mapper"})
public class DaoConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) throws SQLException {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcClient jdbcClient(DataSource dataSource) throws SQLException {
        return JdbcClient.create(dataSource);
    }
}
