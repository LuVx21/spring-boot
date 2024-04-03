package org.luvx.boot.mybatis.mapper.common.utils;

import org.junit.jupiter.api.Test;
import org.luvx.boot.mybatis.mapper.entity.User;
import org.luvx.coding.common.more.MorePrints;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

class ProviderUtilsTest {

    @Test
    void parseObjectTest() throws Exception {
        User user = new User().setId(10010L).setUserName("a").setPassword("b").setAge(10)
                .setBirthday(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ProviderUtils.beanToMap(user);
        System.out.println(map);
    }

    @Test
    void getReadVariablesTest() {
        ProviderUtils.getReadFields(User.class);
        ProviderUtils.getWriteFields(User.class);
    }

    @Test
    void getAllFieldsTest() throws Exception {
        String tableName = ProviderUtils.getTableName(User.class);
        String pkField = ProviderUtils.getPrimaryKeyField(User.class);
        String pk = ProviderUtils.getPrimaryKeyColumn(User.class);
        String[] allFields = ProviderUtils.getAllFields(User.class);
        String[] allColumns = ProviderUtils.getAllColumns(User.class);
        ProviderUtils.CacheEntry cacheEntry = ProviderUtils.allFieldAndColumnMap(User.class);
        String[] selectColumns = ProviderUtils.getSelectColumns(User.class);
        MorePrints.println(
                tableName,
                pkField,
                pk,
                Arrays.toString(allFields),
                Arrays.toString(allColumns),
                cacheEntry,
                Arrays.toString(selectColumns)
        );
    }
}