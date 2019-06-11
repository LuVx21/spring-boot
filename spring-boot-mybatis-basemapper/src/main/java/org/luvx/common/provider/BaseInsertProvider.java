package org.luvx.common.provider;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.utils.ProviderUtils;

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
    // int insertSelectiveList(@Param("records") Collection<T> records);

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
     * 批量插入(实现不了)
     *
     * @param para
     * @return
     */
    public String insertList(Map<String, Object> para, ProviderContext context) {
        Object beans = para.get("records");
        Objects.requireNonNull(beans, "插入对象不可为空");
        Collection records = (Collection) beans;
        Class clazz = ProviderUtils.getEntityClass(context);

        SQL sql = new SQL();
        sql.INSERT_INTO(ProviderUtils.getTableName(clazz));
        String[] columns = ProviderUtils.getColumns(clazz);
        sql.INTO_COLUMNS(columns);

        Iterator iterator = records.iterator();
        boolean flag = false;
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            Object record = iterator.next();
            JSONObject beanObj = ProviderUtils.parseObject(record);
            if (flag) {
                sb.append(", (");
            }
            int fieldSize = beanObj.size() - 1;
            for (String key : beanObj.keySet()) {
                String value = String.format("#{records[" + i + "].%s}", key);
                if (!flag) {
                    sql.INTO_VALUES(value);
                    if (fieldSize == 0) {
                        flag = true;
                    }
                } else {
                    sb.append(value);
                    if (fieldSize != 0) {
                        sb.append(",");
                    } else {
                        sb.append(")");
                    }
                }
                fieldSize--;
            }
            i++;
        }
        System.out.println(sql.toString() + sb.toString());
        return sql.toString() + sb.toString();
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
        Class clazz = bean.getClass();

        SQL sql = new SQL();
        sql.INSERT_INTO(ProviderUtils.getTableName(clazz));

        JSONObject beanObj = ProviderUtils.parseObject(bean);
        for (String key : beanObj.keySet()) {
            if (selective) {
                Object value = beanObj.get(key);
                if (value == null) {
                    continue;
                }
            }
            String columns = String.format("`%s`", ProviderUtils.nameConvert(key));
            String values = String.format("#{record.%s}", key);
            sql.VALUES(columns, values);
        }

        return sql.toString();
    }
}
