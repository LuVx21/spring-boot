package org.luvx.mapper.dynamic1;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import java.sql.JDBCType;
import java.time.LocalDateTime;

public class UserRecord {
    public static final UserRow row = new UserRow();
    public static final SqlColumn<Integer> id = row.id;
    public static final SqlColumn<String> userName = row.userName;
    public static final SqlColumn<String> password = row.password;
    public static final SqlColumn<Integer> age = row.age;
    public static final SqlColumn<LocalDateTime> updateTime = row.updateTime;

    public static class UserRow extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.BIGINT);
        public final SqlColumn<String> userName = column("user_name", JDBCType.VARCHAR);
        public final SqlColumn<String> password = column("password", JDBCType.VARCHAR);
        public final SqlColumn<Integer> age = column("age", JDBCType.INTEGER);
        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public UserRow() {
            super("user");
        }
    }
}
