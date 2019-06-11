package org.luvx.common.provider;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.query.Query;
import org.luvx.common.utils.ProviderUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: org.luvx.common.provider
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 19:08
 */
@Slf4j
public class BaseSelectProvider extends BaseProvider {

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

    /**
     * 批量主键查询
     *
     * @param para
     * @param context
     * @return
     */
    public String selectBatchIds(Map<String, Object> para, ProviderContext context) {
        Class clazz = ProviderUtils.getEntityClass(context);
        String pk = ProviderUtils.getPrimaryKey(clazz);

        Collection<Serializable> ids = (Collection) para.get("ids");
        Objects.requireNonNull(ids, "筛选条件不可为空");

        SQL sql = new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz));

        sql = makeBatchIdWhere(sql, pk, ids);

        return sql.toString();
    }

    /**
     * 条件查询
     *
     * @param para
     * @return
     */
    public String selectSelective(Map<String, Object> para) {
        Object record = para.get("record");
        Objects.requireNonNull(record, "筛选条件不可为空");
        Class clazz = record.getClass();

        SQL sql = new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz));

        sql = makeWhere(sql, record);

        return sql.toString();
    }

    /**
     * 批量条件查询
     *
     * @param para
     * @param context
     * @return
     */
    public String selectSelectiveList(Map<String, Object> para, ProviderContext context) {
        Collection records = (Collection) para.get("records");
        Class clazz = ProviderUtils.getEntityClass(context);
        Objects.requireNonNull(clazz, "无法获取插入对象的类型");

        SQL sql = new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz));
        sql = makeWhere(sql, records);

        return sql.toString();
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
