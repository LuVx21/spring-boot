package org.luvx.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Columns {
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String columnName;
    private BigDecimal ordinalPosition;
    private String columnDefault;
    private String isNullable;
    private String dataType;
    private BigDecimal characterMaximumLength;
    private BigDecimal characterOctetLength;
    private BigDecimal numericPrecision;
    private BigDecimal numericScale;
    private BigDecimal datetimePrecision;
    private String characterSetName;
    private String collationName;
    private String columnType;
    private String columnKey;
    private String extra;
    private String privileges;
    private String columnComment;
}
