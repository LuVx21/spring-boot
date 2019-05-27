package org.luvx.common.base;

import lombok.Data;

/**
 * 基础查询实体
 */
@Data
public class BaseQueryEntity {
    /**
     * 去重复
     */
    protected Boolean distinctCon;

    /**
     * 要查询的列
     */
     protected String selectColumns;

     /**
     * 查询条件
     */
    protected String whereCon;

    /**
     * 排序
     */
    protected String orderCon;

    /**
     * 分组
     */
    protected String groupCon;

    /**
     * having
     */
    protected String havingCon;

    /**
     * limit
     */
    protected String limitCon;
}