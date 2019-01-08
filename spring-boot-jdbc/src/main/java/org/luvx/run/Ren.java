package org.luvx.run;

import java.math.BigDecimal;
import java.sql.*;

public class Ren {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // 入库
        Connection setInfoConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/boot", "root", "1121");
        String insertTable = "insert into t_tables values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String insertColumn = "insert into t_columns values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement setInfoStmt2 = setInfoConn.prepareStatement(insertTable);
        PreparedStatement setInfoStmt3 = setInfoConn.prepareStatement(insertColumn);
        // 读取配置
        Statement stmt0 = setInfoConn.createStatement();
        ResultSet rs0 = stmt0.executeQuery("select * from t_config where isparse = 1 ");

        while (rs0.next()) {
            String host = rs0.getString(1);
            String port = rs0.getString(2);
            String username = rs0.getString(3);
            String password = rs0.getString(4);

            String url = "jdbc:mysql://" + host + ":" + port + "/";

            System.out.println(url + " - " + username + " - " + password);

            Connection getInfoConn = DriverManager.getConnection(url + "?autoReconnect=true", username, password);
            Statement getInfoStmt = getInfoConn.createStatement();

            String sqlTable = "select * from INFORMATION_SCHEMA.tables where table_schema not in ('information_schema', 'mysql', 'performance_schema') ";
            ResultSet rs = getInfoStmt.executeQuery(sqlTable);

            String schemaNamePre = "";
            Connection getRowNumConn = null;
            Statement getRowNumStmt = null;
            ResultSet getRowRs = null;
            System.out.println("获取该用户下所有表的表信息 START");
            int count = 1;
            while (rs.next()) {
                setInfoStmt2.setString(1, host + ":" + port + " " + username);
                setInfoStmt2.setString(2, rs.getString(1));
                setInfoStmt2.setString(3, rs.getString(2));
                setInfoStmt2.setString(4, rs.getString(3));

                String schemaName = rs.getString(2);
                String tableName = rs.getString(3);

                if (!schemaNamePre.equals(schemaName)) {
                    if (getRowNumConn != null) getRowNumConn.close();
                    if (getRowNumStmt != null) getRowNumStmt.close();
                    getRowNumConn = DriverManager.getConnection(url + schemaName + "?autoReconnect=true", username, password);
                    getRowNumStmt = getRowNumConn.createStatement();
                }
                // 执行超过10s的取消执行
                getRowNumStmt.setQueryTimeout(10);
                int rowNums = -1;
                try {
                    getRowRs = getRowNumStmt.executeQuery("select count(1) from " + tableName);
                    while (getRowRs.next()) {
                        rowNums = getRowRs.getInt(1);
                    }
                } catch (Exception e) {
                    rowNums = -1;
                }

                System.out.println(count++ + ": 获取到表: " + schemaName + "." + tableName + " 的行数: " + rowNums);
                if (getRowRs != null) getRowRs.close();
                schemaNamePre = schemaName;

                setInfoStmt2.setString(5, rs.getString(4));
                setInfoStmt2.setString(6, rs.getString(5));
                setInfoStmt2.setBigDecimal(7, rs.getBigDecimal(6));
                setInfoStmt2.setString(8, rs.getString(7));
                setInfoStmt2.setBigDecimal(9, new BigDecimal(rowNums));
                setInfoStmt2.setBigDecimal(10, rs.getBigDecimal(9));
                setInfoStmt2.setBigDecimal(11, rs.getBigDecimal(10));
                setInfoStmt2.setBigDecimal(12, rs.getBigDecimal(11));
                setInfoStmt2.setBigDecimal(13, rs.getBigDecimal(12));
                setInfoStmt2.setBigDecimal(14, rs.getBigDecimal(13));
                setInfoStmt2.setBigDecimal(15, rs.getBigDecimal(14));
                setInfoStmt2.setTimestamp(16, rs.getTimestamp(15));
                setInfoStmt2.setTimestamp(17, rs.getTimestamp(16));
                setInfoStmt2.setTimestamp(18, rs.getTimestamp(17));
                setInfoStmt2.setString(19, rs.getString(18));
                setInfoStmt2.setBigDecimal(20, rs.getBigDecimal(19));
                setInfoStmt2.setString(21, rs.getString(20));
                setInfoStmt2.setString(22, rs.getString(21));
                setInfoStmt2.addBatch();
            }
            setInfoStmt2.executeBatch();
            if (rs != null) rs.close();
            System.out.println("获取该用户下所有表的表信息 END");
            if (getRowNumStmt != null) getRowNumStmt.close();
            if (getRowNumConn != null) getRowNumConn.close();

            // --------------------------------------------------------------------------
            String sqlColumns = "select * from INFORMATION_SCHEMA.columns where table_schema not in ('information_schema', 'mysql', 'performance_schema') ";
            ResultSet rs1 = getInfoStmt.executeQuery(sqlColumns);
            System.out.println("获取该用户下所有表的列信息 START");
            while (rs1.next()) {
                setInfoStmt3.setString(1, host + ":" + port + " " + username);
                setInfoStmt3.setString(2, rs1.getString(1));
                setInfoStmt3.setString(3, rs1.getString(2));
                setInfoStmt3.setString(4, rs1.getString(3));
                setInfoStmt3.setString(5, rs1.getString(4));
                setInfoStmt3.setBigDecimal(6, rs1.getBigDecimal(5));
                setInfoStmt3.setString(7, rs1.getString(6));
                setInfoStmt3.setString(8, rs1.getString(7));
                setInfoStmt3.setString(9, rs1.getString(8));
                setInfoStmt3.setBigDecimal(10, rs1.getBigDecimal(9));
                setInfoStmt3.setBigDecimal(11, rs1.getBigDecimal(10));
                setInfoStmt3.setBigDecimal(12, rs1.getBigDecimal(11));
                setInfoStmt3.setBigDecimal(13, rs1.getBigDecimal(12));
                setInfoStmt3.setBigDecimal(14, rs1.getBigDecimal(13));
                setInfoStmt3.setString(15, rs1.getString(14));
                setInfoStmt3.setString(16, rs1.getString(15));
                setInfoStmt3.setString(17, rs1.getString(16));
                setInfoStmt3.setString(18, rs1.getString(17));
                setInfoStmt3.setString(19, rs1.getString(18));
                setInfoStmt3.setString(20, rs1.getString(19));
                setInfoStmt3.setString(21, rs1.getString(20));
                setInfoStmt3.addBatch();
            }
            setInfoStmt3.executeBatch();
            if (rs1 != null) rs1.close();
            System.out.println("获取该用户下所有表的列信息 END");

            getInfoStmt.close();
            getInfoConn.close();
        }
        if (rs0 != null) rs0.close();
        stmt0.close();
        if (setInfoStmt2 != null) setInfoStmt2.close();
        if (setInfoStmt3 != null) setInfoStmt3.close();
        setInfoConn.close();
    }
}
