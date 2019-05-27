package org.luvx.common.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.base.BaseQueryEntity;
import org.luvx.common.utils.ProviderUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: org.luvx.common.provider
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 19:13
 */
public class BaseInsertProvider {
    // int insert(@Param("record") T record);
    // int insertSelective(@Param("record") T record);
    // int insertList(@Param("records") Collection<T> records);
    // int insertSelectiveList(@Param("records") Collection<T> records);

    public String insert(ProviderContext context, BaseQueryEntity bean) {
        Class clazz = ProviderUtils.getEntityClass(context);
        assert clazz != null;
        SQL sql = new SQL();
        sql.INSERT_INTO(ProviderUtils.getTableName(clazz));
        Set<String> variables = new HashSet<>(Arrays.asList(ProviderUtils.getReadVariables(clazz)));
        JSONObject beanObj = JSON.parseObject(JSON.toJSONString(bean));
        for (String key : beanObj.keySet()) {
            if ("id".equals(key) || !variables.contains(key)) {
                continue;
            }
            sql.VALUES(String.format("`%s`", ProviderUtils.conversionName(key)), String.format("#{%s}", key));
        }
        return sql.toString();
    }

}
