package org.luvx.common;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * BaseMapper 接口
 * 1.以主键查询
 * 2.以对象查询
 * 3.批量查询
 */
public interface BaseMapper<T> {
    // 单数据操作
    int deleteByPrimaryKey(Object id);

    int updateByPrimaryKey(Object id);

    T selectByPrimaryKey(Object id);

    int insert(T record);

    int insertSelective(T record);

    int deleteSelective(T record);

    int updateSelective(T record);

    List<T> selectSelective(T record);

    // 批量操作
    int batDeleteByPrimaryKey(Collection<Object> ids);

    int batUpdateByPrimaryKey(@Param("ids") Collection<Object> ids, @Param("param") String param);

    List<T> batSelectByPrimaryKey(Collection<Object> ids);

    int batInsert(Collection<T> records);

    int batInsertSelective(Collection<T> records);

    int batDeleteSelective(Collection<T> records);

    int batUpdateSelective(@Param("records") Collection<T> records);

    List<T> batSelectSelective(Collection<T> records);
}