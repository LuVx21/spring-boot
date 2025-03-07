package org.luvx.boot.mars.dao.config;

import com.google.common.collect.Maps;
import org.luvx.boot.mars.common.Consts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.concurrent.ConcurrentMap;

@Configuration
public class DuckDBConfig {
    public static final String DUCKDB_HOME = Consts.DATA_HOME + "/duckdb";

    public static final ConcurrentMap<String, JdbcTemplate> map = Maps.newConcurrentMap();

    @Bean("duckdbJdbcClient")
    public JdbcClient duckdbJdbcClient() {
        String path = DUCKDB_HOME + "/main.db";
        JdbcTemplate jdbcTemplate = duckdbUrl(path);
                String ddl = """
                CREATE TABLE IF NOT EXISTS user (
                    id INTEGER PRIMARY KEY,
                    name VARCHAR,
                    score DOUBLE,
                    is_passed BOOLEAN,
                    birth_date DATE,
                    tags VARCHAR[3],
                    list VARCHAR[],
                    ext MAP(VARCHAR, INTEGER),
                    metadata STRUCT(v VARCHAR, i INTEGER)
                );
                """;
        jdbcTemplate.execute(ddl);
        return JdbcClient.create(jdbcTemplate);
    }

    public static JdbcTemplate duckdbUrl(String path) {
        String url = "jdbc:duckdb:" + path;
        return map.computeIfAbsent(path, k -> new JdbcTemplate(new DriverManagerDataSource(url)));
    }
}
