package org.luvx.boot.mybatis.mapper.common.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.luvx.boot.mybatis.mapper.common.annotations.Ignore;

/**
 * 基础查询实体
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class QueryEntity {
    /**
     * 去重复
     */
    @Ignore
    protected Boolean distinctCon = false;

    /**
     * 要查询的列
     */
    @Ignore
    protected String selectColumns;

    /**
     * 查询条件
     */
    @Ignore
    protected String whereCon;

    /**
     * 排序
     */
    @Ignore
    protected String orderCon;

    /**
     * 分组
     */
    @Ignore
    protected String groupCon;

    /**
     * having
     */
    @Ignore
    protected String havingCon;

    /**
     * limit
     */
    @Ignore
    protected String limitCon;
}