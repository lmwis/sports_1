package com.example.sports.dao;

import com.example.sports.bean.Connect;
import com.example.sports.bean.MedalNum;

import java.util.List;

/**
 * @author lmwis on 2019-04-20 21:49
 */
public interface ConnectMapper {
    /**
     *  查询connect表所有信息
     */
    public List<Connect> queryConnectList();
    /**
     *  查询各学院金牌数量情况
     * @return 返回各学院奖牌数类
     */
    public List<MedalNum> queryGoldNumList();
    /**
     *  查询各学院银牌数量情况
     * @return 返回各学院奖牌数类
     */
    public List<MedalNum> querySilverNumList();
    /**
     *  查询各学院铜牌数量情况
     * @return 返回各学院奖牌数类
     */
    public List<MedalNum> queryCopperNumList();
    /**
     * 插入连接表比赛信息
     * @lmwis 修改过
     */
    public void insertConnect(Connect connect);
    /**
     * 修改连接表比赛信息
     * @param athletesId 运动员编号  //运动员id
     * @param collegeId 学院编号
     * @param matchProjectId 比赛项目编号
     * @param rank 名次
     * @param grade 成绩
     * @param oldAthletesId 原来的运动员编号
     * @param oldMatchProjectId 原来的比赛项目编号
     */
    public void updateConnect(int athletesId, int collegeId, int matchProjectId, int rank, int score, String grade, int oldAthletesId, int oldMatchProjectId);

    /**
     * 修改数据
     * @param connect
     */
    public void updateConnectData(Connect connect);


    /**
     * 查询比赛排名
     * @param matchProjectId 比赛项目编号
     * @return 连接表集合
     */
    public List<Connect> queryRank(int matchProjectId);

    /**
     * 根据学院id查询所有相关连接
     * @param id
     * @return
     * @author lmwis
     */
    public List<Connect> selectByCollegeId(Integer id);

    /**
     * 根据运动员id查询其关联
     * @param id
     * @return
     */
    public List<Connect> selectByAuthorId(Integer id);
}
