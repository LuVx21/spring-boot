package org.luvx.mapper.dynamic.base;

import org.apache.ibatis.annotations.Param;
import org.luvx.common.entity.User;

import java.util.List;
import java.util.Map;

public interface BaseDynamicMapper {
    /**
     * tableName: 查询的表名
     * user: 查询条件对象
     *
     * @param argsMap
     * @return
     */
    int insertByCon(Map<String, Object> argsMap);

    /**
     * tableName: 查询的表名
     * user: 查询条件对象
     *
     * @param argsMap
     * @return
     */
    int insertListByCon(Map<String, Object> argsMap);

    /**
     * tableName: 查询的表名
     * user: 查询条件对象
     *
     * @param argsMap
     * @return
     */
    int deleteByCon(Map<String, Object> argsMap);

    /**
     * tableName: 查询的表名
     * user: 查询条件对象
     *
     * @param tableName
     * @param user
     * @return
     */
    int deleteByCon1(@Param("tableName") String tableName, @Param("user") User user);

    /**
     * tableName: 查询的表名
     * user: 查询条件对象
     *
     * @param argsMap
     * @return
     */
    int updateByCon(Map<String, Object> argsMap);

    /**
     * tableName: 查询的表名
     * user: 查询条件对象
     *
     * @param argsMap
     * @return
     */
    List<User> selectByCon(Map<String, Object> argsMap);
}
