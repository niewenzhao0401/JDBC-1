package com.nie3.preparedstatement.crud;

import com.nie3.bean.Order;
import com.nie3.util.JDBCUtils;
import org.junit.Test;


import java.lang.reflect.Field;
import java.sql.*;

public class OrderForQuery {
    @Test
    public  void  testOrderForQuery(){
        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ? ";
        Order order = orderForQuery(sql, 1);
        System.out.println(order);
    }
    /**
     * 针对order 通用操作
     */
    public Order orderForQuery(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);

            }

            //执行结果集
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集的元数据
            int columCount = rsmd.getColumnCount();
            if (rs.next()) {
                Order order = new Order();
                for (int i = 0; i < columCount; i++) {
                    //获取每个列的列值 通过 Result
                    Object columnvalue = rs.getObject(i + 1);
                    //获取列的列名
       //            String columnLabel = rsmd.getColumnName(i + 1);
//获取列的别名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(order, columnvalue);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }

        return null;
    }


    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select order_id,order_name,order_date from `order` where order_id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);
            //执行并返回结果集
            rs = ps.executeQuery();
            while (rs.next()) {


                int id = (int) rs.getObject(1);
                String name = (String) rs.getObject(2);
                Date date = (Date) rs.getObject(3);
                Order order = new Order(id, name, date);
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtils.closeResource(conn, ps, rs);
    }
}
