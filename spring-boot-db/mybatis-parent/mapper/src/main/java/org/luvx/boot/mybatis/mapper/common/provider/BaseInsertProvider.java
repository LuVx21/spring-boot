package org.luvx.boot.mybatis.mapper.common.provider;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.boot.mybatis.mapper.common.utils.ProviderUtils;
import org.luvx.coding.common.util.Asserts;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: org.luvx.common.provider
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 19:13
 */
@Slf4j
public class BaseInsertProvider {
    /**
     * 无选择性插入
     *
     * @param para
     * @return
     */
    public String insert(Map<String, Object> para) {
        return getInsertSql(para, false);
    }

    /**
     * 选择性插入
     *
     * @param para
     * @return
     */
    public String insertSelective(Map<String, Object> para) {
        return getInsertSql(para, true);
    }

    /**
     * 批量插入
     *
     * @param para
     * @return
     */
    public String insertList(Map<String, Object> para, ProviderContext context) {
        Object beans = para.get("records");
        Asserts.checkNotEmpty(beans, "插入对象不可为空");
        Collection<?> records = (Collection<?>) beans;
        Class<?> clazz = ProviderUtils.getEntityClass(context);

        SQL sql = new SQL();
        sql.INSERT_INTO(ProviderUtils.getTableName(clazz));
        String[] columns = ProviderUtils.getAllColumns(clazz);
        sql.INTO_COLUMNS(columns);

        String[] allFields = ProviderUtils.getAllFields(clazz);
        Iterator<?> iterator = records.iterator();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (i != 0) {
                sb.append(", (");
            }
            for (int k = 0; k < allFields.length; k++) {
                String field = allFields[k];
                String value = STR."#{records[\{i}].\{field}}";
                if (i == 0) {
                    sql.INTO_VALUES(value);
                } else {
                    sb.append(value);
                    sb.append(k == allFields.length - 1 ? ")" : ", ");
                }
            }
            i++;
        }
        log.debug("sql: {},拼接:{}", sql, sb);
        return sql + sb.toString();
    }

    /**
     * 生成insert语句
     *
     * @param para      参数map
     * @param selective 是否选择性插入
     * @return
     */
    private String getInsertSql(Map<String, Object> para, boolean selective) {
        Object bean = para.get("record");
        Objects.requireNonNull(bean, "插入对象不可为空");
        Class<?> clazz = bean.getClass();

        SQL sql = new SQL();
        sql.INSERT_INTO(ProviderUtils.getTableName(clazz));

        ProviderUtils.CacheEntry cacheEntry = ProviderUtils.FIELD_COLUMN_CACHE.get(clazz);
        String[] allFields = cacheEntry.getAllField();
        Map<String, String> fieldColumnMap = cacheEntry.getFieldColumnMap();
        Map<String, Object> map = ProviderUtils.beanToMap(bean);
        for (String field : allFields) {
            if (selective && map.get(field) == null) {
                continue;
            }
            sql.VALUES(STR."`\{fieldColumnMap.get(field)}`", STR."#{record.\{field}}");
        }
        return sql.toString();
    }
}
