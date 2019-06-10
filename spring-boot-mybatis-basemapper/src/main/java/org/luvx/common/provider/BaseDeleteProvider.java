package org.luvx.common.provider;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.utils.ProviderUtils;

import java.io.Serializable;
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
public class BaseDeleteProvider {
    /**
     * 以主键删除
     *
     * @param context
     * @return
     */
    public String deleteByPrimaryKey(ProviderContext context) {
        Class clazz = ProviderUtils.getEntityClass(context);
        String pk = ProviderUtils.getPrimaryKey(clazz);
        assert clazz != null;
        return new SQL()
                .DELETE_FROM(ProviderUtils.getTableName(clazz))
                .WHERE(pk + " = #{id}")
                .toString();
    }

    /**
     * 条件删除
     *
     * @param para
     * @return
     */
    public String deleteSelective(Map<String, Object> para) {
        Object bean = para.get("record");
        Objects.requireNonNull(bean, "删除条件不可为空");
        Class clazz = bean.getClass();
        SQL sql = new SQL()
                .DELETE_FROM(ProviderUtils.getTableName(clazz));

        JSONObject beanObj = ProviderUtils.parseObject(bean);
        for (String key : beanObj.keySet()) {
            Object value = beanObj.get(key);
            if (value == null) {
                continue;
            }
            String values = String.format("#{record.%s}", key);
            sql.WHERE(ProviderUtils.conversionName(key) + " = " + values);
        }

        return sql.toString();
    }

    /**
     * 批量主键删除
     *
     * @param para
     * @return
     */
    public String deleteByPrimaryKeyList(Map<String, Object> para, ProviderContext context) {
        Class clazz = ProviderUtils.getEntityClass(context);
        String pk = ProviderUtils.getPrimaryKey(clazz);

        Object bean = para.get("ids");
        Objects.requireNonNull(bean, "删除条件不可为空");
        String in = " in (" + StringUtils.join(bean, ",") + ")";
        Collection<Serializable> collection = (Collection) bean;

        SQL sql = new SQL()
                .DELETE_FROM(ProviderUtils.getTableName(clazz));

        int i = 0;
        Iterator<Serializable> iterator = collection.iterator();

        while (iterator.hasNext()) {
            Serializable id = iterator.next();
            sql.WHERE(pk + " = #{ids[" + i + "]}");
            if (iterator.hasNext()) {
                sql.OR();
                i++;
            }
        }

        return sql.toString();
    }

    /**
     * 批量条件删除
     * 1. 占位符替换存在问题
     *
     * @param para
     * @return
     */
    public String deleteSelectiveList(Map<String, Object> para, ProviderContext context) {
        Object bean = para.get("records");
        Objects.requireNonNull(bean, "删除条件不可为空");
        Collection records = (Collection) bean;
        if (records.size() == 0) {
            return "";
        }

        Class clazz = ProviderUtils.getEntityClass(context);
        Objects.requireNonNull(clazz, "无法获取插入对象的类型");
        SQL sql = new SQL().DELETE_FROM(ProviderUtils.getTableName(clazz));

        Iterator iterator = records.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            JSONObject beanObj = ProviderUtils.parseObject(obj);
            for (String key : beanObj.keySet()) {
                Object value = beanObj.get(key);
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
