package org.luvx.mapper.empty.base;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;

public interface BaseDeleteMapper<T> {
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
}
