package org.luvx.boot.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class TemplateTest extends ApplicationTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void insertTest() {
        String sql = "insert into user (user_name, password, age) values ('foo','bar',15)";
        // sql = "update user set age = 100 where id = 10000;";
        // sql = "delete from user where id = 10000;";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void update1Test() {
        String sql = "insert into user (user_name, password, age) values (?,?,?)";
        // sql = "update user set age = 100 where user_name = ? and password = ? and age = ?";
        // sql = "delete from user where user_name = ? and password = ? and age = ?";

        Object[] array = {"foo0", "bar0", 0};
        jdbcTemplate.update(sql, array);

        List<Object[]> paramList = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            Object[] arr = {"foo" + i, "bar" + i, i};
            paramList.add(arr);
        }

        jdbcTemplate.batchUpdate(sql, paramList);
    }

    @Test
    public void queryTest() {
        String sql = "select * from user where id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, 1_0000);
        while (rowSet.next()) {
            log.info("id:{},userName:{},password:{},age:{}",
                    rowSet.getLong("id"),
                    rowSet.getString("user_name"),
                    rowSet.getString("password"),
                    rowSet.getInt("age")
            );
        }

        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, 1_0000);
        System.out.println(users);

        Map<String, Object> map = jdbcTemplate.queryForMap(sql, 1_0000);
        System.out.println(map);
    }

    /**
     * 直接转成自定义类型
     */
    @Test
    public void queryObjectTest() {
        String sql = "select * from user where id = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);

        User user = jdbcTemplate.queryForObject(sql, rowMapper, 1_0000);
        System.out.println(user);

        List<User> users = jdbcTemplate.query(sql, rowMapper, 1_0000);
        System.out.println(users);

        // 不支持自定义类型, 仅仅支持单列
        // List<User> users1 = jdbcTemplate.queryForList(sql, User.class, 1_0000);
    }
}
