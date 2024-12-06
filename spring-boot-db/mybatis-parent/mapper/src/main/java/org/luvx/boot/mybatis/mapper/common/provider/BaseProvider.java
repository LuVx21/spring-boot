package org.luvx.boot.mybatis.mapper.common.provider;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.boot.mybatis.mapper.common.utils.ProviderUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.luvx.boot.mybatis.mapper.common.utils.ProviderUtils.FIELD_COLUMN_CACHE;

public abstract class BaseProvider {

    /**
     * where条件:批量主键查询用
     * 批量id -> in方式
     * 批量id -> or方式
     *
     * @return
     */
    SQL makeBatchIdWhere(SQL sql, Class<?> clazz, Collection<Serializable> ids) {
        String pk = FIELD_COLUMN_CACHE.get(clazz).getPkColumn();

        // in语句
        String s = IntStream.range(0, ids.size())
                .mapToObj(i -> "#{ids[" + i + "]}")
                .collect(Collectors.joining(", ", pk + " in (", ")"));
        sql.WHERE(s);

        // or语句
        // for (int i = 0; i < ids.size(); i++) {
        //     sql.WHERE(pk + " = #{ids["+i+"]}");
        //     if (i < ids.size() - 1) {
        //         sql.OR();
        //     }
        // }
        return sql;
    }

    /**
     * where条件:单条件查询用
     *
     * @return
     */
    SQL makeWhere(SQL sql, Object record) {
        List<Pair<String, Object>> list = ProviderUtils.beanToArray(record);
        Map<String, String> field2ColumnMap = FIELD_COLUMN_CACHE.get(record.getClass()).getFieldColumnMap();
        for (Pair<String, Object> entry : list) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            sql.WHERE("`" + field2ColumnMap.get(key) + "` = #{record." + key + "}");
        }

        return sql;
    }

    /**
     * where条件:批量条件查询用
     *
     * @return
     */
    SQL makeWhere(SQL sql, Collection<?> records) {
        Iterator<?> iterator = records.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Object record = iterator.next();
            Map<String, String> field2ColumnMap = FIELD_COLUMN_CACHE.get(record.getClass()).getFieldColumnMap();
            List<Pair<String, Object>> list = ProviderUtils.beanToArray(record);
            for (Pair<String, Object> p : list) {
                String key = p.getKey();
                Object value = p.getValue();
                if (value == null) {
                    continue;
                }
                String where = "`" + field2ColumnMap.get(key) + "` = #{records[" + i + "]." + key + "}";
                sql.WHERE(where);
            }
            if (iterator.hasNext()) {
                sql.OR();
                i++;
            }
        }

        return sql;
    }
}
