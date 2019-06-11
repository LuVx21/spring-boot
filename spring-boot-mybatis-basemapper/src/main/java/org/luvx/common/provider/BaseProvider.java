package org.luvx.common.provider;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.utils.ProviderUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: org.luvx.common.provider
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/6/11 10:17
 */
public abstract class BaseProvider {

    /**
     * where条件:批量主键查询用
     * 批量id -> in方式
     * 批量id -> or方式
     *
     * @return
     */
    SQL makeBatchIdWhere(SQL sql, String pk, Collection<Serializable> ids) {
        // in语句
        StringBuilder sb = new StringBuilder(pk).append(" in (");
        for (int i = 0; i < ids.size(); i++) {
            sb.append("#{ids[");
            sb.append(i);
            sb.append("]}");
            if (i < ids.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        sql.WHERE(sb.toString());

        /// or语句
        // for (int i = 0; i < ids.size(); i++) {
        //     sql.WHERE(pk + " = #{ids[" + i + "]}");
        //     if (i < ids.size() - 1) {
        //         sql.OR();
        //     }
        // }

        return sql;
    }

    /**
     *
     * @return
     */
    SQL makeWhere(SQL sql,Object record ){
        Set<Map.Entry<String, Object>> entrySet = ProviderUtils.parseObject(record).entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            sql.WHERE(ProviderUtils.nameConvert(key)
                    + " = "
                    + String.format("#{record.%s}", key));
        }

        return sql;
    }

    /**
     * where条件:批量条件查询用
     *
     * @return
     */
    SQL makeWhere(SQL sql, Collection records) {
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
                sql.WHERE(ProviderUtils.nameConvert(key) + " = " + values);
            }
            if (iterator.hasNext()) {
                sql.OR();
                i++;
            }
        }

        return sql;
    }
}
