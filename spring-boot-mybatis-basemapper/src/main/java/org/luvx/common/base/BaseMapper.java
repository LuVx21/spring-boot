package org.luvx.common.base;

import org.apache.ibatis.annotations.*;
import org.luvx.common.provider.BaseDeleteProvider;
import org.luvx.common.provider.BaseInsertProvider;
import org.luvx.common.provider.BaseSelectProvider;
import org.luvx.common.provider.BaseUpdateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: org.luvx.common.base
 * @Author: Ren, Xie
 * @Date: 2019/5/27 20:15
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
    @InsertProvider(type = BaseInsertProvider.class, method = "insert")
    /// @Options(useGeneratedKeys = true, keyColumn = "id")
    int insert(@Param("record") T record);

    /**
     * 插入数据(严格判空)
     *
     * @param record entity
     * @return int
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "insertSelective")
    int insertSelective(@Param("record") T record);

    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "insertList")
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
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteByPrimaryKey")
    int deleteByPrimaryKey(@Param("id") Serializable id);

    /**
     * 删除
     *
     * @param record
     * @return
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteSelective")
    int deleteSelective(@Param("record") T record);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteByPrimaryKeyList")
    int deleteByPrimaryKeyList(@Param("ids") Collection<Serializable> ids);

    /**
     * 批量删除
     *
     * @param records
     * @return
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteSelectiveList")
    int deleteSelectiveList(@Param("records") Collection<T> records);

    /**
     * 更新数据(不判空)
     *
     * @param record
     * @return
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateByPrimaryKey")
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
    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateSelective")
    int updateSelective(@Param("record") T record, @Param("target") T target);

    /**
     * 批量更新
     *
     * @param ids
     * @param record
     * @return
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateByPrimaryKeyList")
    int updateByPrimaryKeyList(@Param("ids") Collection<Serializable> ids, @Param("record") T record);

    /**
     * 批量更新
     *
     * @param records
     * @param target
     * @return
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "updateSelectiveList")
    int updateSelectiveList(@Param("records") Collection<T> records, @Param("target") T target);

    /**
     * 根据id查询实体
     *
     * @param id
     * @return entity
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectByPrimaryKey")
    T selectByPrimaryKey(@Param("id") Serializable id);

    /**
     * 查询
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectSelective")
    List<T> selectSelective(@Param("record") T record);

    /**
     * 批量查询
     *
     * @param ids
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectBatchIds")
    List<T> selectBatchIds(@Param("ids") Collection<Serializable> ids);

    /**
     * 批量查询
     *
     * @param records
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectSelectiveList")
    List<T> selectSelectiveList(@Param("records") Collection<T> records);
}