package org.luvx.boot.mars.dao.config;

import com.google.common.collect.Maps;
import org.luvx.boot.mars.common.Consts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.concurrent.ConcurrentMap;

@Configuration
public class SqliteConfig {
    public static final String SQLITE_HOME = Consts.DATA_HOME + "/sqlite";

    public static final ConcurrentMap<String, JdbcTemplate> map = Maps.newConcurrentMap();

    @Bean("sqliteJdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        String path = SQLITE_HOME + "/main.db";
        JdbcTemplate jdbcTemplate = sqliteUrl(path);
        String ddl = """
                create table if not exists user
                (
                    id          INTEGER         not null,
                    user_name   TEXT default '' not null,
                    password    TEXT default '' not null,
                    age         INTEGER,
                    birthday    TEXT default '' not null,
                    update_time TEXT default '' not null
                );
                """;
        jdbcTemplate.execute(ddl);
        return jdbcTemplate;
    }

    public static JdbcTemplate sqliteUrl(String path) {
        String url = "jdbc:sqlite:" + path;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource(url));
        map.put(path, jdbcTemplate);
        return jdbcTemplate;
    }
}
