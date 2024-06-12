package org.luvx.boot.jdbc;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.annotation.Resource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JdbcAppTests {
    @Resource
    protected JdbcTemplate jdbcTemplate;
    @Resource
    protected JdbcClient   jdbcClient;
}
