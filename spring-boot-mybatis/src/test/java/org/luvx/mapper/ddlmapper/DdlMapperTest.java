package org.luvx.mapper.ddlmapper;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @ClassName: org.luvx.mapper.ddlmapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/2/21 10:02
 */
// @Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class DdlMapperTest {
    @Autowired
    DdlMapper ddlMapper;

    /**
     * org.springframework.jdbc.BadSqlGrammarException
     */
    @Test
    public void createTable() {
        int result = 0;
        try {
            result = ddlMapper.createTable("mysql.user", "user2");
        } catch (BadSqlGrammarException e) {
            System.out.println("创建新表需要的现有表不存在");
            System.out.println("执行时出异常的sql: " + e.getSql());
            e.printStackTrace();
        }
        System.out.println("执行结果码: " + result);
    }

    /**
     * org.springframework.jdbc.UncategorizedSQLException
     * org.springframework.jdbc.BadSqlGrammarException
     */
    @Test
    public void renameTable() {
        int result = 0;
        try {
            result = ddlMapper.renameTable("user2", "user3");
        } catch (UncategorizedSQLException e) {
            System.out.println("欲重命名的表不存在");
            System.out.println("执行时出异常的sql: " + e.getSql());
            e.printStackTrace();
        } catch (BadSqlGrammarException e) {
            System.out.println("重命名后的表已存在");
            System.out.println("执行时出异常的sql: " + e.getSql());
            e.printStackTrace();
        }
        System.out.println("执行结果码: " + result);
    }

    @Test
    public void dropTable() {
        int result = ddlMapper.dropTable("user3");
        System.out.println(result);
    }
}