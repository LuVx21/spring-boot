package org.luvx.boot.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.boot.jdbc.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class TemplateTest extends JdbcAppTests {
    RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);

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
    void testType() {
        final int id = 1_0000;
        String sql = "select user_name from user where id = ?";
        // 不支持自定义类型,只能使用Integer,String.class等类型
        List<String> users1 = jdbcTemplate.queryForList(sql, String.class, id);
        System.out.println(users1);
        // 不支持自定义类型,只能使用Integer,String.class等类型
        String user = jdbcTemplate.queryForObject(sql, String.class, id);
        System.out.println(user);
    }

    @Test
    public void queryTest() {
        final int id = 1_0000;
        String sql = "select * from user where id = ?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        while (rowSet.next()) {
            log.info("id:{},userName:{},password:{},age:{}",
                    rowSet.getLong("id"),
                    rowSet.getString("user_name"),
                    rowSet.getString("password"),
                    rowSet.getInt("age")
            );
        }

        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, id);
        System.out.println(users);

        Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);
        System.out.println(map);

        jdbcTemplate.queryForStream(sql, rowMapper, id).forEachOrdered(u -> System.out.println(u));
    }

    /**
     * 直接转成自定义类型
     */
    @Test
    public void testRowMapper() {
        int id = 1_0000;
        String sql = "select * from user where id = ?";

        User user = jdbcTemplate.queryForObject(sql, rowMapper, id);
        System.out.println(user);

        List<User> users = jdbcTemplate.query(sql, rowMapper, id);
        System.out.println(users);
    }

    @Test
    void testIn() {
        final List<Integer> ids = List.of(1, 1_0000);
        String sql = "select * from user where id in (:ids)";

        NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, List<Integer>> args = Map.of("ids", ids);
        List<Map<String, Object>> maps = givenParamJdbcTemp.queryForList(sql, args);
        System.out.println(maps);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);
        List<User> data = givenParamJdbcTemp.query(sql, parameters, rowMapper);
        System.out.println(data);
    }
}
