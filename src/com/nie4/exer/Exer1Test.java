package com.nie4.exer;

import com.nie3.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Exer1Test {

    public Exer1Test() {
    }


    @Test
    public void testInsert() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String name = scanner.next();
        System.out.print("请输入邮箱：");
        String email = scanner.next();
        System.out.print("请输入生日：");
        String birthday = scanner.next();
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        int insertCount = this.update(sql, name, email, birthday);
        if (insertCount > 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }

    }


    public int update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for(int i = 0; i < args.length; ++i) {
                ps.setObject(i + 1, args[i]);
            }

            int var7 = ps.executeUpdate();
            return var7;
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }

        return 0;
    }


}
