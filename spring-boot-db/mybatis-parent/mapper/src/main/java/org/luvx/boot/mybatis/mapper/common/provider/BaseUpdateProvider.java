package org.luvx.boot.mybatis.mapper.common.provider;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.boot.mybatis.mapper.common.utils.ProviderUtils;

import java.io.Serializable;
import java.util.*;

public class BaseUpdateProvider extends BaseProvider {

    private SQL makeSet(SQL sql, Object record, Class<?> clazz, boolean isById, boolean selective) {
        ProviderUtils.CacheEntry cacheEntry = ProviderUtils.FIELD_COLUMN_CACHE.get(clazz);
        String[] allField = cacheEntry.getAllField();
        Map<String, String> fieldColumnMap = cacheEntry.getFieldColumnMap();

        String pkField = cacheEntry.getPkField();
        Set<String> variables = Sets.newHashSet(allField);
        String updated = isById ? "record" : "target";
        List<Pair<String, Object>> list = ProviderUtils.beanToArray(record);
        for (Pair<String, Object> p : list) {
            String key = p.getKey();
            Object value = p.getValue();
            // 不可更改主键
            if (Objects.equals(pkField, key) || !variables.contains(key)) {
                continue;
            }
            if (selective && value == null) {
                continue;
            }
            sql.SET(String.format("`%s` = #{%s.%s}", fieldColumnMap.get(key), updated, key));
        }
        return sql;
    }

    /**
     * 以主键更新
     * 不可更新主键
     *
     * @param para
     * @param context
     * @return
     */
    public String updateByPrimaryKey(Map<String, Object> para, ProviderContext context) {
        return update(para, context, false);
    }

    public String updateByPrimaryKeySelective(Map<String, Object> para, ProviderContext context) {
        return update(para, context, true);
    }

    private String update(Map<String, Object> para, ProviderContext context, boolean selective) {
        Object record = para.get("record");
        Class<?> clazz = ProviderUtils.getEntityClass(context);

        ProviderUtils.CacheEntry cacheEntry = ProviderUtils.FIELD_COLUMN_CACHE.get(clazz);
        String pkField = cacheEntry.getPkField(), pkColumn = cacheEntry.getPkColumn();

        SQL sql = new SQL().UPDATE(ProviderUtils.getTableName(clazz));
        sql = makeSet(sql, record, clazz, true, selective);
        sql.WHERE("`" + pkColumn + "` = #{record." + pkField + "}");
        return sql.toString();
    }

    /**
     * 以主键更新
     *
     * @param para
     * @param context
     * @return
     */
    public String updateSelective(Map<String, Object> para, ProviderContext context) {
        Object target = para.get("target");
        Class<?> clazz = ProviderUtils.getEntityClass(context);

        SQL sql = new SQL()
                .UPDATE(ProviderUtils.getTableName(clazz));

        sql = makeSet(sql, target, clazz, false, true);

        Object record = para.get("record");
        sql = makeWhere(sql, record);

        return sql.toString();
    }

    /**
     * 以批量主键更新
     *
     * @param para
     * @param context
     * @return
     */
    public String updateByPrimaryKeyList(Map<String, Object> para, ProviderContext context) {
        Collection<Serializable> ids = (Collection<Serializable>) para.get("ids");
        Object record = para.get("record");
        Class<?> clazz = ProviderUtils.getEntityClass(context);

        SQL sql = new SQL()
                .UPDATE(ProviderUtils.getTableName(clazz));

        sql = makeSet(sql, record, clazz, true, false);

        sql = makeBatchIdWhere(sql, clazz, ids);

        return sql.toString();
    }

    /**
     * 以批量条件更新
     *
     * @param para
     * @param context
     * @return
     */
    public String updateSelectiveList(Map<String, Object> para, ProviderContext context) {
        Object target = para.get("target");
        Class<?> clazz = ProviderUtils.getEntityClass(context);

        SQL sql = new SQL()
                .UPDATE(ProviderUtils.getTableName(clazz));

        sql = makeSet(sql, target, clazz, false, true);

        Collection<?> records = (Collection<?>) para.get("records");
        sql = makeWhere(sql, records);

        return sql.toString();
    }
}
