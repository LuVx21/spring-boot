package org.luvx.jdbc;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luvx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: org.luvx.jdbc
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/21 9:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Ignore
    @Test
    public void executeTest() {
        String sql = "";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void updateTest() {
        String sql = "insert into user (user_name, password, age) values (?,?,?)";
        List<Object[]> paramList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Object[] arr = {"foo" + i, "bar" + i, i};
            paramList.add(arr);
        }
        jdbcTemplate.batchUpdate(sql, paramList);
    }

    @Test
    public void queryTest() {
        String sql = "select * from user where id = 10043;";
        User user = jdbcTemplate.queryForObject(sql, new User());
        System.out.println(user);

        sql = "select * from user;";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);

        sql = "select * from user where id = ?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, 10043);
        System.out.println(map);
    }
}
// https://juejin.im/post/5b583c9de51d451984699900
