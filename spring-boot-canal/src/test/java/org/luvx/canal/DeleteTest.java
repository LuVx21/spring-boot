package org.luvx.canal;

import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName: org.luvx.canal
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/31 14:36
 */
public class DeleteTest {

    @Data
    static class Column {
        private String  name;
        private Object  value;
        private Boolean update;
    }

    private List<Column> columns;

    @Before
    public void init() {
        Column column = new Column();
        column.setName("id");
        column.setValue(1);
        Column column1 = new Column();
        column1.setName("username");
        column1.setValue("foobar");
        columns.add(column);
        columns.add(column1);
    }

    /**
     * delete from user_renxie1 where id = 1;
     */
    @Test
    public void method1() {
        /// 1. 获取数据库, 表名
        String dbName = "boot";
        String tableName = "user_renxie1";
        /// 2. 获取主键, 假设非联合主键
        String pk = "id";
        /// 3. 获取主键的值
        Object pkValue = null;
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (Objects.equals(column.getName(), pk)) {
                pkValue = column.getValue();
                break;
            }
        }
        /// 4. 利用3中获取的主键, 找到ODPS上该数据所在分区
        String odpsTableName = "src_boot_user_renxie1";
        String partition = "dt";
        /// 5. 获取ODPS端表分区字段
        String sql = "select " + partition + " from " + odpsTableName + " where " + pk + "=" + String.valueOf(pkValue);
        String partitionValue = "20190130,20190131";

        /// 5. 重建分区(输出语句即可)

        /*
        alter table user partition( dt = '20190132') rename to partition(dt = '20190132_temp');
        insert into user partition(dt)
        select user_id, gender, age, '20190132' as dt from user where dt = '20190132_temp' AND user_id != 10000 ;
        alter table user drop if exists partition(dt='20190132_temp');
        */

    }
}
