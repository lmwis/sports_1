package com.example.sports.dao;

import com.example.sports.bean.Athletes;
import com.example.sports.bean.UpdateAthletes;

import java.util.List;

/**
 * @author lmwis on 2019-04-20 21:26
 */
public interface AthletesMapper {
    /**
     *  查找所有运动员
     * @return 返回整张运动员表（团体个人）
     */
    public List<Athletes> queryAthletesList();
    /**
     *  根据学号查找运动员   //根据编号
     * @return 返回运动员对象
     */
    public Athletes queryAthleteByStuNum(String stuNum);

    public void updateAthletesByStuNumV1(UpdateAthletes updateAthletes);

    /**
     *  根据学号查找运动员
     * @return 返回运动员对象
     */
    public Athletes queryAthleteById(int id);
    /**
     *  根据姓名查找运动员
     * @return 返回运动员对象
     */
    public Athletes queryAthleteByName(String name);
    /**
     *  根据名称查找团体
     * @return 返回运动员（团体）对象
     */
    public Athletes queryTeamByName(String name);
    /**
     * 根据比赛项目查找运动员
     * @param name 比赛项目
     * @return 运动员表
     */
    public List<Athletes> queryAthletesListByMatchProject(String name);
    /**
     * 添加运动员信息
     * @param name 运动员 姓名
     * @param sex 运动员性别
     * @param collegeId 学院编号
     * @param isTeam 团体/个人
     */
    public void insertAthletes(String stuNum, String name, String sex, int collegeId, String className,int isTeam);
    /**
     * 根据学号删除运动员
     */
    public void deleteAthletesByStuNum(String stuNum);
    /**
     * 根据姓名删除运动员（团体）
     */
    public void deleteAthletesByName(String name);
    /**
     * 根据学号修改运动员信息
     * @param newStuNum 新学号
     * @param name 名字
     * @param sex 性别
     * @param collegeId 学院编号
     * @param className 班级名称
     * @param isTeam 团体/个人
     * @param oldStuNum 旧学号
     */
    public void updateAthletesByStuNum(String StuNum, String name, String sex, int collegeId, String className, String oldStu);
    /**
     * 根据名称修改团体信息
     * @param newStuNum 新学号
     * @param name 名字
     * @param sex 性别
     * @param collegeId 学院编号
     * @param className 班级名称
     * @param isTeam 团体/个人
     * @param oldStuNum 旧学号
     */
    public void updateAthletesByName(String StuNum, String name, String sex, int collegeId, String className, String oldStu);
}
