package org.luvx.common.provider;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.query.Query;
import org.luvx.common.utils.ProviderUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * @ClassName: org.luvx.common.provider
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 19:08
 */
@Slf4j
public class BaseSelectProvider {

    // T selectByPrimaryKey(@Param("id") Serializable id);
    // List<T> selectSelective(@Param("record") T record);
    // List<T> selectBatchIds(@Param("ids") Collection<Serializable> ids);
    // List<T> selectSelectiveList(@Param("records") Collection<T> records);

    /**
     * 主键查询
     *
     * @param context
     * @return
     */
    public String selectByPrimaryKey(ProviderContext context) {
        Class clazz = ProviderUtils.getEntityClass(context);
        String pk = ProviderUtils.getPrimaryKey(clazz);
        Objects.requireNonNull(clazz, "类对象不可为空");
        return new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz))
                .WHERE(pk + " = #{id}")
                .toString();
    }

    public String selectBatchIds(ProviderContext context) {
        Class clazz = ProviderUtils.getEntityClass(context);
        assert clazz != null;
        Objects.requireNonNull(clazz, "类对象不可为空");

        return new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz))
                .WHERE("id = #{ids}")
                .toString();
    }

    public String ids(Collection<Serializable> ids) {
        String sql = "`id` in (" + StringUtils.join(ids, ",")
                + ")";
        return sql;
    }

    public String select(ProviderContext context, Query query) {
        StringBuilder sql = new StringBuilder(selectAll(context, query));
        JSONObject queryObj = ProviderUtils.parseObject(query);
        if (queryObj.get("offset") != null && queryObj.get("limit") != null) {
            sql.append(" limit #{offset}, #{limit}");
        }
        return sql.toString();
    }

    public String selectAll(ProviderContext context, Query query) {
        Class clazz = ProviderUtils.getEntityClass(context);
        assert clazz != null;
        StringBuilder sql = new StringBuilder(new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz))
                .WHERE(ProviderUtils.getWheres(query, clazz))
                .toString());
        JSONObject queryObj = ProviderUtils.parseObject(query);
        if (queryObj.get("sort") != null) {
            sql.append(" order by ").append(queryObj.getString("sort")).append(" ");
            if (queryObj.get("order") != null) {
                sql.append(queryObj.getString("order"));
            }
        }
        return sql.toString();
    }

    public String selectCount(ProviderContext context, Query query) {
        Class clazz = ProviderUtils.getEntityClass(context);
        assert clazz != null;
        return new SQL()
                .SELECT("count(*)")
                .FROM(ProviderUtils.getTableName(clazz))
                .WHERE(ProviderUtils.getWheres(query, clazz))
                .toString();
    }
}
