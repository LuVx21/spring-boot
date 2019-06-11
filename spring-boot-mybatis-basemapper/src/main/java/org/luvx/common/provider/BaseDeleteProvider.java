package org.luvx.common.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.utils.ProviderUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: org.luvx.common.provider
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 19:13
 */
public class BaseDeleteProvider extends BaseProvider {
    /**
     * 以主键删除
     *
     * @param context
     * @return
     */
    public String deleteByPrimaryKey(ProviderContext context) {
        Class clazz = ProviderUtils.getEntityClass(context);
        String pk = ProviderUtils.getPrimaryKey(clazz);
        return new SQL()
                .DELETE_FROM(ProviderUtils.getTableName(clazz))
                .WHERE(pk + " = #{id}")
                .toString();
    }

    /**
     * 批量主键删除
     *
     * @param para
     * @return
     */
    public String deleteByPrimaryKeyList(Map<String, Object> para, ProviderContext context) {
        Class clazz = ProviderUtils.getEntityClass(context);

        SQL sql = new SQL()
                .DELETE_FROM(ProviderUtils.getTableName(clazz));

        String pk = ProviderUtils.getPrimaryKey(clazz);
        Collection<Serializable> ids = (Collection) para.get("ids");
        sql = makeBatchIdWhere(sql, pk, ids);

        return sql.toString();
    }

    /**
     * 条件删除
     *
     * @param para
     * @return
     */
    public String deleteSelective(Map<String, Object> para) {
        Object record = para.get("record");
        Class clazz = record.getClass();

        SQL sql = new SQL()
                .DELETE_FROM(ProviderUtils.getTableName(clazz));

        sql = makeWhere(sql, record);

        return sql.toString();
    }

    /**
     * 批量条件删除
     *
     * @param para
     * @return
     */
    public String deleteSelectiveList(Map<String, Object> para, ProviderContext context) {
        Collection records = (Collection) para.get("records");
        Class clazz = ProviderUtils.getEntityClass(context);
        Objects.requireNonNull(clazz, "无法获取插入对象的类型");

        SQL sql = new SQL()
                .DELETE_FROM(ProviderUtils.getTableName(clazz));

        sql =makeWhere(sql,records);

        return sql.toString();
    }
}
