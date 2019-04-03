package org.luvx.module.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: org.luvx.module.generator.entity
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/4/1 20:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDO {
    private String         dbName;
    private String         tableName;
    private ColumnDO       pk;
    private List<ColumnDO> columns;
    private String         comment;
    private String         engine;
}
