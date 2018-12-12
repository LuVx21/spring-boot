package org.luvx.common;

/**
 * 基础查询实体
 */
public class BaseQueryEntity {
    // 要查询的列
    private String selectColumns;

    // 查询条件
    private String whereCon;

    // 排序
    private String orderCon;

    public String getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(String selectColumns) {
        this.selectColumns = selectColumns;
    }

    public String getWhereCon() {
        return whereCon;
    }

    public void setWhereCon(String whereCon) {
        this.whereCon = whereCon;
    }

    public String getOrderCon() {
        return orderCon;
    }

    public void setOrderCon(String orderCon) {
        this.orderCon = orderCon;
    }
}