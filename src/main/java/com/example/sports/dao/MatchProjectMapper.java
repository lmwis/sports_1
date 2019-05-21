package com.example.sports.dao;

import com.example.sports.bean.MatchProject;

import java.util.List;
import java.util.Map;

/**
 * @author lmwis on 2019-04-20 21:51
 */
public interface MatchProjectMapper {
    /**
     * 插入比赛项目
     * @param name 比赛项目名称
     * @param type 比赛项目类型
     * @param time 比赛时间
     */
    public void insertMatchProject(String name, int type, String time);
    /**
     * 根据比赛名称删除比赛项目
     * @param name
     */
    public void deleteMatchProjectByName(String name);
    /**
     * 根据比赛名称修改比赛
     * @param name 新比赛名称
     * @param type 比赛类型
     * @param time 比赛类型
     * @param oldName 旧的比赛名称
     */
    public void updateMatchProjectByName(String name, int type, String time, String oldName);
    /**
     * 查询所有比赛项目
     * @return 比赛项目集合
     */
    public List<MatchProject> queryMatchProjectList();
    /**
     * 根据名称查找比赛项目
     * @param name 比赛名称
     * @return 比赛项目对象
     */
    public MatchProject queryMatchProjectByName(String name);
    List<MatchProject> queryMatchProjectLimit(Map<String, Object> map);
    public Integer getCount(Map<String, Object> map);

    /**
     * 根据id查找比赛项目
     * @param id
     * @return
     * @author lmwis
     */
    public MatchProject queryMatchProjectById(int id);
}

