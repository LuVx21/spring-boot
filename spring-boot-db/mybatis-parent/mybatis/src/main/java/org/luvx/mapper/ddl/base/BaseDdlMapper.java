package org.luvx.mapper.ddl.base;

import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;

public interface BaseDdlMapper {
    /**
     * 以现有表创建新表
     *
     * @param oldTableName
     * @param newTableName
     * @return
     * @throws UncategorizedSQLException 欲重命名的表不存在
     * @throws BadSqlGrammarException    重命名后的表已存在
     */
    int createTable(@Param("oldTableName") String oldTableName, @Param("newTableName") String newTableName)
            throws UncategorizedSQLException, BadSqlGrammarException;

    /**
     * 删除表
     *
     * @param oldTableName
     * @return
     */
    int dropTable(@Param("oldTableName") String oldTableName);

    /**
     * 重命名表
     *
     * @param oldTableName
     * @param newTableName
     * @return
     * @throws BadSqlGrammarException 创建新表需要的现有表不存在
     */
    int renameTable(@Param("oldTableName") String oldTableName, @Param("newTableName") String newTableName)
            throws BadSqlGrammarException;

    // TODO alert
}
