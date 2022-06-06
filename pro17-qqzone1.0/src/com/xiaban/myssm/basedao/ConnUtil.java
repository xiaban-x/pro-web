package com.xiaban.myssm.basedao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static Connection createConn(){
        try {
            InputStream is = ConnUtil.class.getClassLoader().getResourceAsStream("jdbcF.properties");
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
    public static Connection getConn(){
        Connection conn = threadLocal.get();
        if (conn == null){
            conn = createConn();
            threadLocal.set(conn);
        }
        return threadLocal.get();
    }
    public static void closeConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null){
            return;
        }
        if (!conn.isClosed()){
            conn.close();
            threadLocal.set(null);
        }
    }
}
