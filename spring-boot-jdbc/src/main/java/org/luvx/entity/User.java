package org.luvx.entity;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: org.luvx.jdbc
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/21 9:21
 */
@Data
public class User implements RowMapper<User> {
    private Long    id;
    private String  userName;
    private String  password;
    private Integer age;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUserName(rs.getString("user_name"));
        user.setPassword(rs.getString("password"));
        user.setAge(rs.getInt("age"));
        return user;
    }
}
