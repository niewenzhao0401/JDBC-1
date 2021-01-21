package com.nie5.bean.Customer;

import com.nie3.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertTest {
    public InsertTest() {
    }

    @Test
    public void testInsert1() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 20000; i++) {
                ps.setObject(1, "name_" + i);
                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println("花费时间是:" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }

    }


    @Test
    public void testInsert2() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);


            for (int i = 1; i <= 20000; i++) {
                ps.setObject(1, "name_" + i);
                //1.“攒”sql
                ps.addBatch();
                if (i % 500 == 0) {
                    //2执行
                    ps.executeBatch();
                    //3 清空
                    ps.clearBatch();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("花费时间是:" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }

    }


    @Test
    public void testInsert3() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();


            //设置为不自动提交数据
            conn.setAutoCommit(false);


            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);


            for (int i = 1; i <= 20000; i++) {
                ps.setObject(1, "name_" + i);
                //1.“攒”sql
                ps.addBatch();
                if (i % 500 == 0) {
                    //2执行
                    ps.executeBatch();
                    //3 清空
                    ps.clearBatch();
                }
            }

            //2.提交数据
            conn.commit();

            long end = System.currentTimeMillis();
            System.out.println("花费时间是:" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }

    }

}
