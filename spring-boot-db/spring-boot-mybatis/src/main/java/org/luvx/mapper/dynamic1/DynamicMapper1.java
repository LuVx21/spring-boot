package org.luvx.mapper.dynamic1;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.luvx.common.entity.User;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.apache.ibatis.type.JdbcType.*;
import static org.luvx.mapper.dynamic1.UserRecord.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Repository
public interface DynamicMapper1 extends
        CommonCountMapper,
        CommonInsertMapper<User>,
        CommonDeleteMapper,
        CommonUpdateMapper,
        CommonSelectMapper {
    String key = "allCol";
    BasicColumn[] selectList = BasicColumn.columnList(id.as("A_ID"), userName, password, age, updateTime);

    default int insert(User record) {
        return MyBatis3Utils.insert(this::insert, record, row, c -> c.map(id).toProperty("id")
                .map(userName).toProperty("userName")
                .map(password).toProperty("password")
                .map(age).toProperty("age")
                .map(updateTime).toProperty("updateTime")
        );
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, row, completer);
    }

    default int deleteByPrimaryKey(Integer id_) {
        return delete(c ->
                c.where(id, isEqualTo(id_))
        );
    }

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = key, value = {
            @Result(column = "id", property = "id", jdbcType = BIGINT, id = true),
            @Result(column = "user_name", property = "userName", jdbcType = VARCHAR),
            @Result(column = "password", property = "password", jdbcType = VARCHAR),
            @Result(column = "age", property = "age", jdbcType = INTEGER),
            @Result(column = "update_time", property = "updateTime", jdbcType = TIMESTAMP)
    })
    List<User> selectList(SelectStatementProvider selectStatement);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap(key)
    Optional<User> selectOne(SelectStatementProvider selectStatement);

    default Optional<User> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, row, completer);
    }

    default List<User> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectList, selectList, row, completer);
    }
}
