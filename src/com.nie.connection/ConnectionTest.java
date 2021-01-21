package com.nie.connection;

import com.sun.deploy.util.Property;
import javafx.scene.control.SplitPane;
import org.junit.Test;


import javax.swing.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    //方式一
    @Test
    public void testconntection() throws SQLException {

        //实现类接口 获取Driver实现类对象
        Driver driver = new com.mysql.jdbc.Driver();

        //jdbc:mysql 表示协议
        // localhost  IP 地址
        //3306 mysql的端口号
        // test 数据库名
        String url = "jdbc:mysql://localhost:3306/test";
        //将用户名和密码写在Properties
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123123");

        Connection conn = driver.connect(url, info);


        System.out.println(conn);
    }


    //方式二  对方式一的迭代 在如下的程序中不出现第三方分api 使得程序具有更好的可移植性
    @Test
    public void testconntection2() throws Exception {
        //获取Driver实现类对象 使用反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //获取要连接的数据库
        String url = "jdbc:mysql://localhost:3306/test";
        //将用户名和密码写在Properties
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123123");


        //获取连接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }


    //方式三: 使用Divermanager替换diver
    @Test
    public void testconntection3() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        //1.获取driver的实例对象
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();


        //另外三个的基本连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123123";

        //注册驱动
        DriverManager.registerDriver(driver);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }


    //方式四: 可以自是加载驱动
    @Test
    public void testconntection4() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        //1另外三个的基本连接信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123123";


        //2.加载驱动
//        Class.forName("com.mysql.jdbc.Driver");


//        Driver driver = (Driver) clazz.newInstance();
//        //注册驱动
//        DriverManager.registerDriver(driver);

        /*
        在mysql数据中声明了一下的操作
        static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException var1) {
            throw new RuntimeException("Can't register driver!");
        }
    }*/


        //3 获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }


    //方式三: 使用Divermanager替换diver
    @Test
    public void testconntection5() throws Exception {


//读取配置文件的基本信息
        InputStream is = ConnectionTest.class.getClassLoader().
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
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

}
