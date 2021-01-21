package com.nie3.preparedstatement.crud;

import com.nie3.bean.Custormer;
import com.nie3.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 针对Customer表的查询
 */
public class CustomerForQuery {
    @Test
    public void testQueryForCustomers2() {}

    @Test
    public void testQueryForCustomers() {
        String sql = "select id,name,email,birth from customers where id= ?";
        Custormer custormer = queryForCustomers(sql, 10);
        System.out.println(custormer);

        String sql1 = "select name,email from customers where name = ?";
        Custormer customer1 = queryForCustomers(sql1, "周杰伦");
        System.out.println(customer1);


    }


    /**
     * 针对Customers表的 通用操作
     */
    public Custormer queryForCustomers(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData 获取结果集的
            int columncount = rsmd.getColumnCount();
            if (rs.next()) {
                //实例化一个对象
                Custormer cust = new Custormer();

                for (int i = 0; i < columncount; i++) {
                    //获取列值
                    Object columnvalue = rs.getObject(i + 1);


                    //获取每个列的列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    //给cust指定的某个属性 复制columnvalue 通过反射
                    Field field = Custormer.class.getDeclaredField(columnLabel);//返回字字段
                    field.setAccessible(true);//这是字段的可访问性
                    field.set(cust, columnvalue);

                }
                return cust;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }

        return null;

    }

    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id= ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);
            //执行并返回结果集
            resultSet = ps.executeQuery();

            //处理结果集
            if (resultSet.next()) {//判断结果集的下一条是否又数据  如果有返回true 指针并下移
                //获取当前当前各个字段的值
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                //方式一.
                //   System.out.println(id +name+email+birth);

                //方式二
                Object[] date = new Object[]{id, name, email, birth};
                //方法三
                Custormer custormer = new Custormer(id, name, email, birth);
                System.out.println(custormer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JDBCUtils.closeResource(conn, ps, resultSet);
    }
}
