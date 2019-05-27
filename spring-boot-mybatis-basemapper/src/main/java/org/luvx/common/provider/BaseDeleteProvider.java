package org.luvx.common.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.luvx.common.utils.ProviderUtils;

/**
 * @ClassName: org.luvx.common.provider
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 19:13
 */
public class BaseDeleteProvider {


    // int deleteByPrimaryKey(@Param("id") Serializable id);
    // int deleteSelective(@Param("record") T record);
    // int deleteByPrimaryKeyList(@Param("ids") Collection<Serializable> ids);
    // int deleteSelectiveList(@Param("records") Collection<T> records);

    public String deleteByPrimaryKey(ProviderContext context) {
        Class clazz = ProviderUtils.getEntityClass(context);
        assert clazz != null;
        return new SQL()
                .DELETE_FROM(ProviderUtils.getTableName(clazz))
                .WHERE("`id` = #{id}")
                .toString();
    }
}
