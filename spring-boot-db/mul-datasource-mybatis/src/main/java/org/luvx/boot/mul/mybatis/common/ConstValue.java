package org.luvx.boot.mul.mybatis.common;

public interface ConstValue {
    String dynamicDataSource        = "dynamicDataSource";
    String dynamicSqlSessionFactory = "dynamicSqlSessionFactory";
    // --------------------------------------------------------

    String prefix_write            = "spring.dynamic.datasource.write";
    String ds_write                = "write";
    String writeSqlSessionFactory  = ds_write + "SqlSessionFactory";
    String writeTransactionManager = ds_write + "TransactionManager";

    String prefix_read            = "spring.dynamic.datasource.read";
    String ds_read                = "read";
    String readSqlSessionFactory  = ds_read + "SqlSessionFactory";
    String readTransactionManager = ds_read + "TransactionManager";
}
