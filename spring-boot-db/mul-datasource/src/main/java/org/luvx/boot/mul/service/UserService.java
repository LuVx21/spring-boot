package org.luvx.boot.mul.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource(name = "dataSource0Template")
    private JdbcTemplate dataSource0Template;
    @Resource(name = "dataSource1Template")
    private JdbcTemplate dataSource1Template;

    public void m1() {
        dataSource0Template.update("update user set user_name = '10000' where id = 10000;");
        dataSource1Template.update("update user set user_name = '10001' where id = 10000;");
    }
}
