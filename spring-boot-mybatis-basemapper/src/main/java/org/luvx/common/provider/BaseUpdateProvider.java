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
public class BaseUpdateProvider {

    // int updateByPrimaryKey(@Param("record") T record);
    //// int updateByPrimaryKeySelective(@Param("record") T record);
    // int updateSelective(@Param("record") T record, @Param("target") T target);
    // int updateByPrimaryKeyList(@Param("ids") Collection<Serializable> ids, @Param("record") T record);
    // int updateSelectiveList(@Param("records") Collection<T> records, @Param("target") T target);

    /**
     * 以主键更新
     * 不可更新主键
     *
     * @param para
     * @param context
     * @return
     */
    public String updateByPrimaryKey(Map<String, Object> para, ProviderContext context) {
        Object bean = para.get("record");
        Class clazz = ProviderUtils.getEntityClass(context);
        String pkField = ProviderUtils.getPrimaryKeyField(clazz);

        SQL sql = new SQL();
        sql.UPDATE(ProviderUtils.getTableName(clazz));
        Set<String> variables = new HashSet<>(Arrays.asList(ProviderUtils.getWriteFields(clazz)));

        JSONObject beanObj = ProviderUtils.parseObject(bean);
        for (String key : beanObj.keySet()) {
            if (Objects.equals(pkField, key) || !variables.contains(key)) {
                continue;
            }
            if (beanObj.get(key) == null) {
                continue;
            }
            sql.SET(String.format("`%s` = #{record.%s}", ProviderUtils.conversionName(key), key));
        }

        sql.WHERE(ProviderUtils.conversionName(pkField) + " = " + String.format("#{record.%s}", pkField));
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
        Object record = para.get("record");
        Object target = para.get("target");
        Class clazz = ProviderUtils.getEntityClass(context);
        String pkField = ProviderUtils.getPrimaryKeyField(clazz);

        SQL sql = new SQL();
        sql.UPDATE(ProviderUtils.getTableName(clazz));
        Set<String> variables = new HashSet<>(Arrays.asList(ProviderUtils.getWriteFields(clazz)));

        JSONObject beanObj = ProviderUtils.parseObject(target);
        for (String key : beanObj.keySet()) {
            if (Objects.equals(pkField, key) || !variables.contains(key)) {
                continue;
            }
            if (beanObj.get(key) == null) {
                continue;
            }
            sql.SET(String.format("`%s` = #{target.%s}", ProviderUtils.conversionName(key), key));
        }

        JSONObject recordObj = ProviderUtils.parseObject(record);
        for (String key : recordObj.keySet()) {
            if (recordObj.get(key) == null) {
                continue;
            }
            sql.WHERE(ProviderUtils.conversionName(key) + " = " + String.format("#{record.%s}", key));
        }

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
        Object ids = para.get("ids");
        Object record = para.get("record");
        Class clazz = ProviderUtils.getEntityClass(context);
        String pkField = ProviderUtils.getPrimaryKeyField(clazz);

        SQL sql = new SQL();
        sql.UPDATE(ProviderUtils.getTableName(clazz));
        Set<String> variables = new HashSet<>(Arrays.asList(ProviderUtils.getWriteFields(clazz)));

        JSONObject beanObj = ProviderUtils.parseObject(record);
        for (String key : beanObj.keySet()) {
            if (Objects.equals(pkField, key) || !variables.contains(key)) {
                continue;
            }
            if (beanObj.get(key) == null) {
                continue;
            }
            sql.SET(String.format("`%s` = #{record.%s}", ProviderUtils.conversionName(key), key));
        }

        Collection<Serializable> collection = (Collection) ids;
        int i = 0;
        Iterator<Serializable> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Serializable id = iterator.next();
            sql.WHERE(ProviderUtils.conversionName(pkField) + " = #{ids[" + i + "]}");
            if (iterator.hasNext()) {
                sql.OR();
                i++;
            }
        }

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

        SQL sql = new SQL();
        sql.UPDATE(ProviderUtils.getTableName(clazz));
        Set<String> variables = new HashSet<>(Arrays.asList(ProviderUtils.getWriteFields(clazz)));

        JSONObject beanObj = ProviderUtils.parseObject(target);
        for (String key : beanObj.keySet()) {
            if (Objects.equals(pkField, key) || !variables.contains(key)) {
                continue;
            }
            if (beanObj.get(key) == null) {
                continue;
            }
            sql.SET(String.format("`%s` = #{target.%s}", ProviderUtils.conversionName(key), key));
        }

        Object records = para.get("records");
        Collection collection = (Collection) records;
        Iterator iterator = collection.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            JSONObject a = ProviderUtils.parseObject(obj);
            for (String key : a.keySet()) {
                Object value = a.get(key);
                if (value == null) {
                    continue;
                }
                String values = String.format("#{records[" + i + "].%s}", key);
                sql.WHERE(ProviderUtils.conversionName(key) + " = " + values);
            }
            if (iterator.hasNext()) {
                sql.OR();
                i++;
            }
        }

        return sql.toString();
    }
}
