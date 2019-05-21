package com.example.sports.controller.viewobject;

/**
 * 提供查询的对象封装
 * @author lmwis on 2019-04-21 9:00
 */
public class Athletes4QueryView {
    /*id*/
    private int id;
    /*运动员编号*/
    private String stuNum;
    /*运动员姓名*/
    private String name;
    /*性别*/
    private String sex;
    /*班级*/
    private String className;
    /*运动员对应学院的编号*/
    private int collegeId;
    private String match;   //比赛项目(外键)
    private String grade;  //成绩
    private String score; //积分
    private String rank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
