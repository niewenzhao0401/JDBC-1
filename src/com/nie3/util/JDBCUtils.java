package com.nie3.util;

import com.nie.connection.ConnectionTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作数据库的工类
 */
public class JDBCUtils {

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        //1读取配置文件的基本信息,并加载
        InputStream is = ClassLoader.getSystemClassLoader().
                getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);
        // 读取基本信息
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverclass = pros.getProperty("driverclass");

        //加载驱动
        Class.forName(driverclass);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;

    }

    /**
     * 关闭资源操作
     * @param conn
     * @param ps
     */
    public static void closeResource(Connection conn, Statement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 关闭资源操作
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection conn, Statement ps,ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
