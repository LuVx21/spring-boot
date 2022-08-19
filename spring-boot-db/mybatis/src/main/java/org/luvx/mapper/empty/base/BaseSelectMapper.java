package org.luvx.mapper.empty.base;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseSelectMapper<T> {
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
