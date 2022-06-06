package com.xiaban.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtilsQQZoneDb {
    public static Connection getConn(){
        //1.获取Driver实现类的对象
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//这一步其实也可以省略，因为在jdbc的META-INF的services中的java.sql.Driver已经加载了
            //Driver driver = (Driver) clazz.newInstance();
            //MySQL加载的时候已经进行类加载了

            //2.提供另外三个连接的基本信息
            String url = "jdbc:mysql://localhost:3306/qqzonedb?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
            String user = "root";
            String password = "980892894Hx@";

            //3.注册驱动
            //DriverManager.registerDriver(driver);
            //MySQL已经在类加载的时候就已经注册了Driver
            //4.获取连接
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Connection getConnection(){
        try {
            InputStream is = JDBCUtilsQQZoneDb.class.getClassLoader().getResourceAsStream("jdbcF.properties");
            //InputStream is = new FileInputStream(new File("jdbcF.properties"));
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
            InputStream is = JDBCUtilsQQZoneDb.class.getClassLoader().getResourceAsStream("druid.properties");
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
