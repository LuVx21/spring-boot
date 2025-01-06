package org.luvx.boot.mars.dao.config;

import com.google.common.collect.Maps;
import org.luvx.boot.mars.common.Consts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
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
                create table if not exists common_key_value
                (
                    id           INTEGER           not null constraint pk primary key autoincrement,
                    biz_type     INTEGER           not null,
                    common_key   TEXT              not null,
                    common_value TEXT              not null,
                    invalid      INTEGER default 0 not null,
                    create_time  INTEGER default 0 not null,
                    update_time  INTEGER default 0 not null,
                    constraint uk_biz_type_common_key
                        unique (biz_type, common_key)
                );
                """;
        jdbcTemplate.execute(ddl);
        return jdbcTemplate;
    }

    @Bean("sqliteJdbcClient")
    public JdbcClient jdbcClient(@Qualifier("sqliteJdbcTemplate") JdbcTemplate jdbcTemplate) throws SQLException {
        return JdbcClient.create(jdbcTemplate);
    }


    public static JdbcTemplate sqliteUrl(String path) {
        String url = "jdbc:sqlite:" + path;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource(url));
        map.put(path, jdbcTemplate);
        return jdbcTemplate;
    }
}
