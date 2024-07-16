package org.luvx.mapper.anno;

import org.apache.ibatis.annotations.*;
import org.luvx.common.entity.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.*;

/**
 * @author renxie
 */
@Mapper
@Repository("annoUserMapper")
public interface UserMapper {
    String key = "allCol";

    /**
     * 新增
     *
     * @param record
     * @return
     */
    @Insert({
            "insert into user (id, user_name, password, age)",
            "values (#{id,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
            "#{password,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User record);

    /**
     * 选择新增
     *
     * @param record
     * @return
     * @see UserSqlProvider#insertSelective
     */
    @InsertProvider(type = UserSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertSelective(User record);

    /**
     * 批量插入
     * Param的配置必须是 list, 否则主键回填不起作用
     *
     * @param records
     * @return
     * @see UserSqlProvider#insertList
     */
    @Insert({
            "<script>",
            "insert into user (user_name, password, age)",
            "values",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.userName, jdbcType=VARCHAR}, #{item.password, jdbcType=VARCHAR}, #{item.age,javaType=INT})",
            "</foreach>",
            "</script>"
    })
    // @DeleteProvider(type = UserSqlProvider.class, method = "insertList")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertList(@Param("list") Collection<User> records);

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Delete({
            "delete from user",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * 条件删除
     *
     * @param record
     * @return
     * @see UserSqlProvider#deleteSelective
     */
    @DeleteProvider(type = UserSqlProvider.class, method = "deleteSelective")
    int deleteSelective(User record);

    /**
     * 批量主键删除
     *
     * @param ids
     * @return
     * @see UserSqlProvider#deleteByPrimaryKeyList
     */
    @DeleteProvider(type = UserSqlProvider.class, method = "deleteByPrimaryKeyList")
    int deleteByPrimaryKeyList(@Param("ids") Collection<Serializable> ids);

    /**
     * 批量条件删除
     *
     * @param records
     * @return
     * @see UserSqlProvider#deleteSelectiveList
     */
    @DeleteProvider(type = UserSqlProvider.class, method = "deleteSelectiveList")
    int deleteSelectiveList(@Param("records") Collection<User> records);

    /**
     * 更新全部字段
     *
     * @param record
     * @return
     */
    @Update({
            "update user",
            "set user_name = #{userName,jdbcType=VARCHAR},",
            "password = #{password,jdbcType=VARCHAR},",
            "age = #{age,jdbcType=INTEGER}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(User record);

    /**
     * 更新部分字段
     *
     * @param record
     * @return
     * @see UserSqlProvider#updateByPrimaryKeySelective
     */
    @UpdateProvider(type = UserSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新
     *
     * @param record
     * @param target
     * @return
     * @see UserSqlProvider#updateSelective
     */
    @UpdateProvider(type = UserSqlProvider.class, method = "updateSelective")
    int updateSelective(@Param("record") User record, @Param("target") User target);

    /**
     * 批量更新
     *
     * @param ids
     * @param record
     * @return
     * @see UserSqlProvider#updateByPrimaryKeyList
     */
    @UpdateProvider(type = UserSqlProvider.class, method = "updateByPrimaryKeyList")
    int updateByPrimaryKeyList(@Param("ids") Collection<Serializable> ids, @Param("record") User record);

    /**
     * 批量更新
     *
     * @param records
     * @param target
     * @return
     * @see UserSqlProvider#updateSelectiveList
     */
    @UpdateProvider(type = UserSqlProvider.class, method = "updateSelectiveList")
    int updateSelectiveList(@Param("records") Collection<User> records, @Param("target") User target);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Select({
            "select",
            "id, user_name, password, age, update_time",
            "from user",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results(id = key, value = {
            @Result(column = "id", property = "id", jdbcType = BIGINT, id = true),
            @Result(column = "user_name", property = "userName", jdbcType = VARCHAR),
            @Result(column = "password", property = "password", jdbcType = VARCHAR),
            @Result(column = "age", property = "age", jdbcType = INTEGER),
            @Result(column = "update_time", property = "updateTime", jdbcType = TIMESTAMP)
    })
    User selectByPrimaryKey(Long id);

    /**
     * 查询
     *
     * @param record
     * @return
     * @see UserSqlProvider#selectSelective
     */
    @ResultMap(key)
    @SelectProvider(type = UserSqlProvider.class, method = "selectSelective")
    List<User> selectSelective(@Param("record") User record);

    /**
     * 批量查询
     *
     * @param ids
     * @return
     * @see UserSqlProvider#selectByPrimaryKeyList
     */
    @ResultMap(key)
    @SelectProvider(type = UserSqlProvider.class, method = "selectByPrimaryKeyList")
    List<User> selectByPrimaryKeyList(@Param("ids") Collection<Serializable> ids);

    /**
     * 批量查询
     *
     * @param records
     * @return
     * @see UserSqlProvider#selectSelectiveList
     */
    @ResultMap(key)
    @SelectProvider(type = UserSqlProvider.class, method = "selectSelectiveList")
    List<User> selectSelectiveList(@Param("records") Collection<User> records);

    @Insert("${paramSQL}")
    void dynamicsInsert(@Param("paramSQL") String sql);

    @Update("${paramSQL}")
    void dynamicsUpdate(@Param("paramSQL") String sql);

    @Delete("${paramSQL}")
    void dynamicsDelete(@Param("paramSQL") String sql);

    @Select("${paramSQL}")
    List<User> dynamicsSelect(@Param("paramSQL") String sql);
}