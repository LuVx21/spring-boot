package org.luvx.mapper.dynamicmapper;

import org.apache.ibatis.annotations.Param;
import org.luvx.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: org.luvx.mapper.dynamicmapper
 * @Description: 动态表名
 * @Author: Ren, Xie
 * @Date: 2019/2/21 17:28
 */
@Repository
public interface DynamicMapper {
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
