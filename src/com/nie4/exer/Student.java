package com.nie4.exer;

public class Student {
    private int flowID;
    private int type;
    private String IDCard;
    private String examCard;
    private String name;
    private String location;
    private int grade;

    public Student() {
    }

    public Student(int flowID, int type, String iDCard, String examCard, String name, String location, int grade) {
        this.flowID = flowID;
        this.type = type;
        this.IDCard = iDCard;
        this.examCard = examCard;
        this.name = name;
        this.location = location;
        this.grade = grade;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIDCard() {
        return this.IDCard;
    }

    public void setIDCard(String iDCard) {
        this.IDCard = iDCard;
    }

    public String getExamCard() {
        return this.examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getFlowID() {
        return this.flowID;
    }

    public String toString() {
        System.out.println("=========查询结果===========");
        return this.info();
    }

    private String info() {
        return "流水号：" + this.flowID + "\n四级/六级：" + this.type + "\n身份证号：" + this.IDCard + "\n准考证号：" + this.examCard + "\n学生姓名：" + this.name + "\n区域：" + this.location + "\n成绩：" + this.grade;
    }
}