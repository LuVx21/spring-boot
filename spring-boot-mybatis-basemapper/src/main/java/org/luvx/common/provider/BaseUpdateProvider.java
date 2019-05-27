package org.luvx.common.provider;

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
public class BaseUpdateProvider {

    // int updateByPrimaryKey(@Param("record") T record);
    // int updateByPrimaryKeySelective(@Param("record") T record);
    // int updateSelective(@Param("record") T record, @Param("target") T target);
    // int updateByPrimaryKeyList(@Param("ids") Collection<Serializable> ids, @Param("record") T record);
    // int updateSelectiveList(@Param("records") Collection<T> records, @Param("target") T target);


    public String updateByPrimaryKey(ProviderContext context, BaseQueryEntity bean) {
        Class clazz = ProviderUtils.getEntityClass(context);
        assert clazz != null;
        JSONObject beanObj = ProviderUtils.parseObject(bean);
        SQL sql = new SQL();
        sql.UPDATE(ProviderUtils.getTableName(clazz));
        Set<String> variables = new HashSet<>(Arrays.asList(ProviderUtils.getReadVariables(clazz)));
        for (String key : beanObj.keySet()) {
            if ("id".equals(key) || !variables.contains(key)) {
                continue;
            }
            sql.SET(String.format("`%s`=#{%s}", ProviderUtils.conversionName(key), key));
        }
        sql.WHERE("`id`=#{id}");
        return sql.toString();
    }
}
