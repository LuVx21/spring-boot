package org.luvx.mapper.dynamic1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.common.entity.User;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DynamicMapper1Test {
    @Autowired
    DynamicMapper1 mapper;

    @Test
    public void selectList() {
        SelectStatementProvider selectStatement = select(DynamicMapper1.selectList)
                .from(UserRecord.row)
                .where(UserRecord.id, isEqualTo(1))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        List<User> users = mapper.selectList(selectStatement);
        System.out.println(users);
    }
}