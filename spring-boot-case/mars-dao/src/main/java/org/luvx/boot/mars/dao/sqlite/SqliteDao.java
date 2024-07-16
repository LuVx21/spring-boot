package org.luvx.boot.mars.dao.sqlite;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class SqliteDao {
    @Resource(name = "sqliteJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
}
