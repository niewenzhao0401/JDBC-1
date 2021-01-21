package com.nie4.exer;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import com.nie3.util.JDBCUtils;
import org.junit.Test;

public class Exer2Test {

    public Exer2Test() {
    }

    @Test
    public void testInterst() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("四级/六级：");
        int type = scanner.nextInt();
        System.out.print("身份证号：");
        String IDCard = scanner.next();
        System.out.print("准考证号：");
        String examCard = scanner.next();
        System.out.print("学生姓名：");
        String studentName = scanner.next();
        System.out.print("所在城市：");
        String location = scanner.next();
        System.out.print("考试成绩：");
        int grade = scanner.nextInt();

        String sql = "insert into examstudent(type,IDCard,examCard,studentName,location,grade)values(?,?,?,?,?,?)";
        int update = this.update(sql, type, IDCard, examCard, studentName, location, grade);
        if (update > 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }

    }

    public int update(String sql, Object... ages) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < ages.length; i++) {
                ps.setObject(i + 1, ages[i]);

            }
            int var = ps.executeUpdate();
            return var;
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
        return 0;
    }


    public void queryWithIDCardOrExamCard() {
        System.out.println("请选择您要输入的类型：");
        System.out.println("a.准考证号");
        System.out.println("b.身份证号");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.next();
        String IDCard;
        String sql;
        Student student;
        if ("a".equalsIgnoreCase(selection)) {
            System.out.println("请输入准考证号");
            IDCard = scanner.next();
            sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where examCard = ?";
            student = (Student) this.getInstance(Student.class, sql, IDCard);
            if (student != null) {
                System.out.println(student);
            } else {
                System.out.println("输入的准考证号有误！");
            }

        } else if ("b".equalsIgnoreCase(selection)) {
            System.out.println("请输入身份证号：");
            IDCard = scanner.next();
            sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where IDCard = ?";
            student = (Student) this.getInstance(Student.class, sql, IDCard);
            if (student != null) {
                System.out.println(student);
            } else {
                System.out.println("输入的身份证号有误！");
            }
        } else {
            System.out.println("您的输入有误，请重新进入程序。");
        }
    }

    public <T> Object getInstance(Class<T> Clazz, String sql, Object... ages) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;//获取结果集
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < ages.length; i++) {
                ps.setObject(i + 1, ages[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();//获取元数据

            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                T t = Clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获得数据值
                    Object columnValue = rs.getObject(i + 1);
                    //获得列表名
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    Field field = Clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                Object var15 = t;
                return var15;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }


        return null;
    }

    @Test
    public void testDeleteByExamCard() {
        System.out.println("请输入学生学号");
        Scanner scanner = new Scanner(System.in);
        String examCard = scanner.next();
        String sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where examCard = ?";
        Student student = (Student) this.getInstance(Student.class, sql, examCard);
        if (student == null) {
            System.out.println("查无此人");
        } else {
            String sql1 = "delete from examstudent where examCard = ?";
            int update = this.update(sql1, examCard);
            if (update > 0) {
                System.out.println("删除成功");
            }
        }
    }

    @Test
    public void testDeleteByExamCard1() {
        System.out.println("请输入学生的考号");
        Scanner scanner = new Scanner(System.in);
        String examCard = scanner.next();
        String sql = "delete from examstudent where examCard = ?";
        int update = this.update(sql, examCard);
        if (update > 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("查无此人，请重新输入");
        }

    }


}
