package com.nie3.preparedstatement.crud;

import com.nie.connection.ConnectionTest;
import com.nie3.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Properties;

/*
使用PreparedStatement 来替换statment
增删改查
 */
public class PreparedStatementUpdateTest {



    @Test
    public void testCommonUpate() {
      /*  String sql = "DELETE from customers  where id = ?";
        update(sql, 3);*/

        String sql = "update  `order` set order_name= ? where order_id= ?";
        update(sql,"adasd","2");
    }


    //通用的得增增删改
    public void update(String sql, Object... args) {//sql中占位符得长度与形参长度一致
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1获取数据库连接
            conn = JDBCUtils.getConnection();
            //2 预编译sql语句 返回PreparedStatement得实例
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);//小心参数声明错误
            }
            //4执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5 关闭资源
            JDBCUtils.closeResource(conn, ps);
        }

    }

    @Test
    public void testDelect() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1获取数据库连接
            conn = JDBCUtils.getConnection();


            //2 预编译sql语句 返回PreparedStatement得实例
            String sql = "DELETE from customers  where id = ?";

            ps = conn.prepareStatement(sql);

            //3填充占位符
            ps.setObject(1, 18);

            //4 执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5资源关闭
            JDBCUtils.closeResource(conn, ps);
        }


    }

    //向customer修改一条记录
    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1获取数据库连接
            conn = JDBCUtils.getConnection();


            //2 预编译sql语句 返回PreparedStatement得实例
            String sql = "update customers set name = ? where id = ?";

            ps = conn.prepareStatement(sql);

            //3填充占位符
            ps.setObject(1, "莫扎特");
            ps.setObject(2, "18");
            //4 执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5资源关闭
            JDBCUtils.closeResource(conn, ps);
        }


    }


    //向customer表中添加一条数据

    @Test
    public void testconntection5() {
        Connection connection = null;
        PreparedStatement ps = null;


        try {
            //读取配置文件的基本信息
            InputStream is = ClassLoader.getSystemClassLoader().
                    getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverclass = pros.getProperty("driverclass");

//加载驱动
            Class.forName(driverclass);
            //获取连接
            connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);

            //4预编sql
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            ps = connection.prepareStatement(sql);

            //填充占位符
            ps.setString(1, "哪吒");
            ps.setString(2, "nezha@gmail.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1000-01-01");
            ps.setDate(3, (new Date(date.getTime())));

            //6执行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //7资源关闭
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
