package org.luvx.boot.mybatis.mapper.common.provider;

import com.google.common.collect.BiMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.boot.mybatis.mapper.common.base.QueryEntity;
import org.luvx.boot.mybatis.mapper.common.query.Query;
import org.luvx.boot.mybatis.mapper.common.utils.ProviderUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        Class<?> clazz = ProviderUtils.getEntityClass(context);
        String pk = ProviderUtils.FIELD_COLUMN_CACHE.get(clazz).getPkColumn();
        Objects.requireNonNull(clazz, "类对象不可为空");
        return new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz))
                .WHERE(STR."`\{pk}` = #{id}")
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
        Class<?> clazz = ProviderUtils.getEntityClass(context);

        Collection<Serializable> ids = (Collection<Serializable>) para.get("ids");
        Objects.requireNonNull(ids, "筛选条件不可为空");

        SQL sql = new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz));

        sql = makeBatchIdWhere(sql, clazz, ids);

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
        Class<?> clazz = record.getClass();

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
        Collection<?> records = (Collection<?>) para.get("records");
        Class<?> clazz = ProviderUtils.getEntityClass(context);
        Objects.requireNonNull(clazz, "无法获取插入对象的类型");

        SQL sql = new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz));
        sql = makeWhere(sql, records);

        return sql.toString();
    }


    /**
     * 自拼写sql
     */
    public String selectCustomer(Map<String, Object> para, ProviderContext context) {
        QueryEntity entity = (QueryEntity) para.get("record");
        String selectColumns = entity.getSelectColumns(), whereCon = entity.getWhereCon();

        Class<?> clazz = ProviderUtils.getEntityClass(context);
        ProviderUtils.CacheEntry cacheEntry = ProviderUtils.FIELD_COLUMN_CACHE.get(clazz);
        BiMap<String, String> columnFieldMap = cacheEntry.getFieldColumnMap().inverse();
        String selectColumn = Arrays.stream(selectColumns.split(","))
                .map(String::trim)
                .map(c -> STR."`\{c}` as \{columnFieldMap.get(c)}")
                .collect(Collectors.joining(", "));

        SQL sql = new SQL();
        if (entity.getDistinctCon()) {
            sql.SELECT_DISTINCT(selectColumn);
        } else {
            sql.SELECT(selectColumn);
        }
        sql.FROM(ProviderUtils.getTableName(clazz));
        sql.WHERE(whereCon);

        return sql.toString();
    }

    public String selectCount(ProviderContext context, Query<?> query) {
        Class<?> clazz = ProviderUtils.getEntityClass(context);
        return new SQL()
                .SELECT("count(*)")
                .FROM(ProviderUtils.getTableName(clazz))
                .WHERE(ProviderUtils.getWheres(query, clazz))
                .toString();
    }

    public String select(ProviderContext context, Query<?> query) {
        StringBuilder sql = new StringBuilder(selectAll(context, query));
        Map<String, Object> queryObj = ProviderUtils.beanToMap(query);
        if (queryObj.get("offset") != null && queryObj.get("limit") != null) {
            sql.append(" limit #{offset}, #{limit}");
        }
        return sql.toString();
    }

    private String selectAll(ProviderContext context, Query<?> query) {
        Class<?> clazz = ProviderUtils.getEntityClass(context);
        assert clazz != null;
        StringBuilder sql = new StringBuilder(new SQL()
                .SELECT(ProviderUtils.getSelectColumns(clazz))
                .FROM(ProviderUtils.getTableName(clazz))
                .WHERE(ProviderUtils.getWheres(query, clazz))
                .toString());
        Map<String, Object> queryObj = ProviderUtils.beanToMap(query);
        if (queryObj.get("sort") != null) {
            sql.append(" order by ").append(MapUtils.getString(queryObj, "sort")).append(" ");
            if (queryObj.get("order") != null) {
                sql.append(MapUtils.getString(queryObj, "order"));
            }
        }
        return sql.toString();
    }
}
