package org.luvx.common;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * BaseMapper 接口
 * 1.以主键查询
 * 2.以对象查询
 * 3.批量查询
 */
public interface BaseMapper<T> {

    /**
     * 插入数据(不判空) (采纳)
     *
     * @param record entity
     * @return int
     */
    int insert(@Param("record") T record);

    /**
     * 插入数据(严格判空)
     *
     * @param record entity
     * @return int
     */
    int insertSelective(@Param("record") T record);

    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    int insertList(@Param("records") Collection<T> records);

    /**
     * 同insertList 废弃
     *
     * @param records
     * @return
     */
    @Deprecated
    int insertSelectiveList(@Param("records") Collection<T> records);

    /**
     * 根据id删除数据
     *
     * @param id
     * @return int
     */
    int deleteByPrimaryKey(@Param("id") Serializable id);

    /**
     * 删除
     *
     * @param record
     * @return
     */
    int deleteSelective(@Param("record") T record);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteByPrimaryKeyList(@Param("ids") Collection<Serializable> ids);

    /**
     * 批量删除
     *
     * @param records
     * @return
     */
    int deleteSelectiveList(@Param("records") Collection<T> records);

    /**
     * 更新数据(不判空)
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(@Param("record") T record);

    /**
     * 更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(@Param("record") T record);

    /**
     * 更新
     *
     * @param record
     * @param target
     * @return
     */
    int updateSelective(@Param("record") T record, @Param("target") T target);

    /**
     * 批量更新
     *
     * @param ids
     * @param record
     * @return
     */
    int updateByPrimaryKeyList(@Param("ids") Collection<Serializable> ids, @Param("record") T record);

    /**
     * 批量更新
     *
     * @param records
     * @param target
     * @return
     */
    int updateSelectiveList(@Param("records") Collection<T> records, @Param("target") T target);

    /**
     * 根据id查询实体
     *
     * @param id
     * @return entity
     */
    T selectByPrimaryKey(@Param("id") Serializable id);

    /**
     * 查询
     *
     * @param record
     * @return
     */
    List<T> selectSelective(@Param("record") T record);

    /**
     * 批量查询
     *
     * @param ids
     * @return
     */
    List<T> selectByPrimaryKeyList(@Param("ids") Collection<Serializable> ids);

    /**
     * 批量查询
     *
     * @param records
     * @return
     */
    List<T> selectSelectiveList(@Param("records") Collection<T> records);
}