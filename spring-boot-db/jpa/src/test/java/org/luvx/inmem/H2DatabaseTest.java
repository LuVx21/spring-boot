package org.luvx.inmem;

import org.junit.jupiter.api.Test;
import org.luvx.ApplicationTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @ClassName: org.luvx.inmem
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/21 19:23
 */
public class H2DatabaseTest extends ApplicationTests {
    private static final String JDBC_URL     = "jdbc:h2:~/test";
    private static final String USER         = "sa";
    private static final String PASSWORD     = "";
    private static final String DRIVER_CLASS = "org.h2.Driver";

    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method() throws Exception {
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();

        stmt.execute("drop table if exists user");
        stmt.execute("create table user(id varchar(36) primary key,user_name varchar(100),password varchar(40))");

        for (int i = 0; i < 6; i++) {
            String userName = "foobar:" + i;
            String sql = "insert into user values('" + i + "','" + userName + "','F')";
            stmt.executeUpdate(sql);
        }

        stmt.executeUpdate("delete from user where user_name='foobar:1'");

        stmt.executeUpdate("update user set user_name='zzz' where user_name='foobar:0'");

        ResultSet rs = stmt.executeQuery("select * from user");
        while (rs.next()) {
            System.out.println(
                    rs.getString("id") + ","
                            + rs.getString("user_name") + ","
                            + rs.getString("password")
            );
        }

        stmt.close();
        conn.close();
    }
}
