package org.luvx.common.provider;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.utils.ProviderUtils;

import java.io.Serializable;
import java.util.*;

/**
 * @ClassName: org.luvx.common.provider
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 19:13
 */
public class BaseUpdateProvider extends BaseProvider {

    SQL makeSet(SQL sql, Object record, Class clazz, String pkField, boolean isById) {
        Set<String> variables = new HashSet<>(ProviderUtils.getAllFields(clazz));
        JSONObject beanObj = ProviderUtils.parseObject(record);
        String updated = isById ? "record" : "target";
        for (String key : beanObj.keySet()) {
            if (Objects.equals(pkField, key) || !variables.contains(key)) {
                continue;
            }
            if (beanObj.get(key) == null) {
                continue;
            }
            sql.SET(String.format("%s = #{%s.%s}", ProviderUtils.nameConvert(key), updated, key));
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
        Object record = para.get("record");
        Class clazz = ProviderUtils.getEntityClass(context);
        String pkField = ProviderUtils.getPrimaryKeyField(clazz);

        SQL sql = new SQL();
        sql.UPDATE(ProviderUtils.getTableName(clazz));
        sql = makeSet(sql, record, clazz, pkField, true);

        sql.WHERE(ProviderUtils.nameConvert(pkField) + " = " + String.format("#{record.%s}", pkField));
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
        Class clazz = ProviderUtils.getEntityClass(context);
        String pkField = ProviderUtils.getPrimaryKeyField(clazz);

        SQL sql = new SQL()
                .UPDATE(ProviderUtils.getTableName(clazz));

        sql = makeSet(sql, target, clazz, pkField, false);

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
        Object record = para.get("record");
        Class clazz = ProviderUtils.getEntityClass(context);
        String pkField = ProviderUtils.getPrimaryKeyField(clazz);

        SQL sql = new SQL()
                .UPDATE(ProviderUtils.getTableName(clazz));

        sql = makeSet(sql, record, clazz, pkField, true);

        String pk = ProviderUtils.nameConvert(pkField);
        Collection<Serializable> ids = (Collection) para.get("ids");
        sql = makeBatchIdWhere(sql, pk, ids);

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
        Class clazz = ProviderUtils.getEntityClass(context);
        String pkField = ProviderUtils.getPrimaryKeyField(clazz);

        SQL sql = new SQL()
                .UPDATE(ProviderUtils.getTableName(clazz));

        sql = makeSet(sql, target, clazz, pkField, false);

        Collection records = (Collection) para.get("records");
        sql = makeWhere(sql, records);

        return sql.toString();
    }
}
