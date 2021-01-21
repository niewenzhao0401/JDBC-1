package com.nie5.bean.Customer;

import com.nie3.bean.Custormer;
import com.nie3.util.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

public class BlobTest {
    public BlobTest() {
    }

    @Test
    public void testInsert() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1, "hhhhh");
        ps.setObject(2, "erdan@qq.com");
        ps.setObject(3, "1999-01-01");
        FileInputStream is = new FileInputStream(new File("girl.jpg"));
        ps.setBlob(4, is);
        ps.execute();
        JDBCUtils.closeResource(conn, ps);
    }

@Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 21);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");
                Custormer cust = new Custormer(id, name, email, birth);
                System.out.println(cust);
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream("erdan.jpg");
                byte[] buffer = new byte[1024];

                int len;
                while((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(conn, ps, rs);
        }



    }


}
