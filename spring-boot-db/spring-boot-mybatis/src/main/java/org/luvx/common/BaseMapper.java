package org.luvx.common;

/**
 * BaseMapper 接口
 * 1.以主键查询
 * 2.以对象查询
 * 3.批量查询
 */
public interface BaseMapper<T> extends
        BaseInsertMapper<T>,
        BaseDeleteMapper<T>,
        BaseUpdateMapper<T>,
        BaseSelectMapper<T> {
}