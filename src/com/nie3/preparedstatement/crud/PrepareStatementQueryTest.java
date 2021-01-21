package com.nie3.preparedstatement.crud;

import com.nie3.bean.Custormer;
import com.nie3.bean.Order;
import com.nie3.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用PrepareStatement实现并针对不同的表的查询操作
 * 使用PreparedStatement实现针对于不同表的通用的查询操作
 */
public class PrepareStatementQueryTest {


    @Test
    public void testGetForList() throws Exception {

        String sql = "select id,name,email from customers where id < ?";
        List<Custormer> list = getForList(Custormer.class,sql,12);
        list.forEach(System.out::println);

        String sql1 = "select order_id orderId,order_name orderName from `order`";
        List<Order> orderList = getForList(Order.class, sql1);
        orderList.forEach(System.out::println);
    }

    public <T> List<T> getForList(Class<T> clazz, String sql, Object... args) throws Exception {
        //连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<T> list = null;
        try {
            conn = JDBCUtils.getConnection();

            //预编译数据库
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i <args.length ; i++) {
                ps.setObject(i+1,args[i]);
            }

            //执行结果集
            rs = ps.executeQuery();
            //获取集的元数据
            //对象中列的类型和属性信息的对象
            ResultSetMetaData rsmd = rs.getMetaData();
            //元数据的列数
            int columnCount = rsmd.getColumnCount();
            list = new ArrayList<>();
            while (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i <columnCount ; i++) {
                    //获取列值
                    Object columVslue = rs.getObject(i + 1);
                    //获取列的列明
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,columVslue);
                }
                list.add(t);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(conn, ps, rs);
        return list;

    }


    @Test
    public void testGetInstance() throws Exception {
        String sql = "select id,name,email from customers where id = ?";
        Custormer customer = getInstance(Custormer.class, sql, 12);
        System.out.println(customer);

        String sql1 = "select order_id orderId,order_name orderName from `order` where order_id = ?";
        Order order = getInstance(Order.class, sql1, 1);
        System.out.println(order);
    }

    public <T> T getInstance(Class<T> clazz, String sql, Object... args) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //连接数据库
            conn = JDBCUtils.getConnection();
            //sql预编译
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            //执行结果集
            rs = ps.executeQuery();

            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();

            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取每个列值
                    Object columValue = rs.getObject(i + 1);
                    //获取每个列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //给t对象指定的columnName属性,值位colunValue 通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);

                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }
}
