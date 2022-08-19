package org.luvx.mapper.empty.base;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;

public interface BaseUpdateMapper<T> {
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
}
