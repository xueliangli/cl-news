package com.clnews.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-17 21:37
 **/
public class DBHelper {
    public static final String driver_class = "com.mysql.jdbc.Driver";
    public static final String driver_url = "jdbc:mysql://localhost:3306/clnews?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
    public static final String user = "root";
    public static final String password = "19960130";

    private static Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rst = null;

    public DBHelper() {
        try {
            conn = getConnInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnInstance() {
        if(conn == null){
            try {
                Class.forName(driver_class);
                conn = DriverManager.getConnection(driver_url, user, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Connect success.");
        }
        return conn;
    }

    public void close() {
        try {
            if (pst != null) {
                this.pst.close();
            }
            if (rst != null) {
                this.rst.close();
            }
            if (conn != null) {
                conn.close();
            }
            System.out.println("Close connection success.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * query
     *
     * @param sql
     * @param sqlValues
     * @return ResultSet
     */
    public ResultSet executeQuery(String sql, List<String> sqlValues) {
        try {
            pst = conn.prepareStatement(sql);
            if (sqlValues != null && sqlValues.size() > 0) {
                setSqlValues(pst, sqlValues);
            }
            rst = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }

    /**
     * update
     *
     * @param sql
     * @param sqlValues
     * @return result
     */
    public int executeUpdate(String sql, List<String> sqlValues) {
        int result = -1;
        try {
            pst = conn.prepareStatement(sql);
            if (sqlValues != null && sqlValues.size() > 0) {
                setSqlValues(pst, sqlValues);
            }
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * sql set value
     *
     * @param pst
     * @param sqlValues
     */
    private void setSqlValues(PreparedStatement pst, List<String> sqlValues) {
        for (int i = 0; i < sqlValues.size(); i++) {
            try {
                pst.setObject(i + 1, sqlValues.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
