package com.xiaban.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {

    public static Connection getConnection(){
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);

            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");

            //2.加载驱动
            Class.forName(driverClass);

            //3.获取连接
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeResource(Connection conn, Statement ps){
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResource(Connection conn, Statement ps, ResultSet rs){
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DataSource source;
    static{
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);
            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection02(){
        try {
            return source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
