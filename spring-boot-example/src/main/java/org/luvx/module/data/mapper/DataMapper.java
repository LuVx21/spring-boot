package org.luvx.module.data.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: org.luvx.module.data.mapper
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/1 20:22
 */
@Repository
public interface DataMapper {
    /**
     * 取得当前库中的所有的表
     *
     * @return
     */
    @Select("select table_name tableName, engine, table_comment tableComment" +
            "from information_schema.tables " +
            "where table_schema = (select database()) " +
            "order by createTime desc")
    List<Map<String, Object>> listTables();

    /**
     * 获取指定表的表信息
     *
     * @param tableName
     * @return
     */
    @Select("select table_name tableName, engine, table_comment tableComment " +
            "from information_schema.tables " +
            "where table_schema = (select database()) and table_name = #{tableName}")
    Map<String, String> getTable(String tableName);

    /**
     * 获取指定表的列信息
     *
     * @param tableName
     * @return
     */
    @Select("select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra " +
            "from information_schema.columns " +
            "where table_name = #{tableName} and table_schema = (select database()) " +
            "order by ordinal_position")
    List<Map<String, String>> listColumns(String tableName);
}
