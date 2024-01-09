package org.luvx.boot.jdbc.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Demo {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        Connection setInfoConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boot", "root", "1121");
        // PreparedStatement setInfoStmt2 = setInfoConn.prepareStatement("show create table user;");
        Statement getInfoStmt = setInfoConn.createStatement();
        ResultSet rs = getInfoStmt.executeQuery("show create table user;");
        while (rs.next()) {
            String tableName = rs.getString("Table");
            String ddl = rs.getString("Create Table");
            System.out.println(tableName);
            System.out.println(ddl);
        }
    }
}
