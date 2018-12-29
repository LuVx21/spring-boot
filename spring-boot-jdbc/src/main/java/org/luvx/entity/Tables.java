package org.luvx.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Tables {
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String engine;
    private BigDecimal version;
    private String rowFormat;
    private BigDecimal tableRows;
    private BigDecimal avgRowLength;
    private BigDecimal dataLength;
    private BigDecimal maxDataLength;
    private BigDecimal indexLength;
    private BigDecimal dataFree;
    private BigDecimal autoIncrement;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp checkTime;
    private String tableCollation;
    private BigDecimal checksum;
    private String createOptions;
    private String tableComment;
}
