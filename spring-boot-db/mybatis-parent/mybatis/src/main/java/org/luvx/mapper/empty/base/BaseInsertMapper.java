package org.luvx.mapper.empty.base;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface BaseInsertMapper<T> {
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
    int insertList(Collection<T> records);

    /**
     * 同insertList 废弃
     *
     * @param records
     * @return
     */
    @Deprecated
    int insertSelectiveList(@Param("records") Collection<T> records);
}
