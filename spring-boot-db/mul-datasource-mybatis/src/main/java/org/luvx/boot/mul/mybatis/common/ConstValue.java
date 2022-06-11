package org.luvx.boot.mul.mybatis.common;

public interface ConstValue {
    String dynamicDataSource        = "dynamicDataSource";
    String dynamicSqlSessionFactory = "dynamicSqlSessionFactory";
    // --------------------------------------------------------

    String prefix_ds1            = "spring.dynamic.datasource.ds1";
    String ds_ds1                = "ds1";
    String ds1SqlSessionFactory  = ds_ds1 + "SqlSessionFactory";
    String ds1TransactionManager = ds_ds1 + "TransactionManager";

    String prefix_ds2            = "spring.dynamic.datasource.ds2";
    String ds_ds2                = "ds2";
    String ds2SqlSessionFactory  = ds_ds2 + "SqlSessionFactory";
    String ds2TransactionManager = ds_ds2 + "TransactionManager";
}
