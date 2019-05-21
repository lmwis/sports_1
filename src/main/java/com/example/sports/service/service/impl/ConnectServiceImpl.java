package com.example.sports.service.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.sports.bean.Athletes;
import com.example.sports.bean.CollegeTotal;
import com.example.sports.bean.Connect;
import com.example.sports.bean.MatchProject;
import com.example.sports.controller.viewobject.MatchRank;
import com.example.sports.dao.*;
import com.example.sports.error.BusinessException;
import com.example.sports.error.EmBusinessError;
import com.example.sports.service.CollegeService;
import com.example.sports.service.service.CollegeServiceV1;
import com.example.sports.service.service.ConnectServiceV1;
import com.example.sports.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 连接表对应接口实现
 * @author Misaki
 *
 */
@Service
public class ConnectServiceImpl implements ConnectServiceV1 {
	@Autowired
	ConnectMapper connectMapper;
	@Autowired
	AthletesMapper athletesMapper;
	@Autowired
	MatchProjectMapper matchProjectMapper;
	@Autowired
	CollegeMapper collegeMapper;
	/**
	 * 插入连接表信息（运动员id, 学院id, 比赛项目id）
	 * @param type 比赛类型（个人/团体）
	 * @param message 运动员信息，个人则是学号，团体则是名称
	 * @param matchProjectName 比赛项目名称
	 */
	public void insertConnect(int type, String message, String matchProjectName) throws BusinessException {
		if(message == null || "".equals(message.trim())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		if(matchProjectName == null || "".equals(matchProjectName.trim())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		//查到对应的运动员/团体信息
		Athletes athlete = judgeAthleteType(type,message);
		if(athlete == null) {
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"运动员不存在");
		}
		//找到对应比赛项目信息
		MatchProject matchProject = matchProjectMapper.queryMatchProjectByName(matchProjectName);
		Connect connect = new Connect();
		connect.setCollegeId(athlete.getCollegeId());
		connect.setAthletesId(athlete.getId());
		connect.setMatchProjectId(matchProject.getId());
		connectMapper.insertConnect(connect);
	}
	/**
	 * 修改连接表信息
	 * @param type 类型，个人/团体
	 * @param message 信息，个人则是学号，团体则是名称
	 * @param matchProjectName 比赛项目名称 
	 * @param rank 排名
	 * @param grade 成绩
	 */
	public void updateConnect(int type, String message, String matchProjectName,
							  int rank, String grade, int score) throws BusinessException {
		if(message == null || "".equals(message.trim())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		if(matchProjectName == null || "".equals(matchProjectName.trim())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		//找到新旧的运动员信息
//		Athletes athlete = new Athletes();//新的运动员信息
//		Athletes oldAthlete = new Athletes();
		Athletes athlete = judgeAthleteType(type,message);
		if(athlete == null) {
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"运动员不存在");
		}
		List<Connect> connects = connectMapper.selectByAuthorId(athlete.getId());
		MatchProject matchProject = matchProjectMapper.queryMatchProjectByName(matchProjectName);
		boolean flag=true;
		for(Connect c :connects){
			if(c.getMatchProjectId()==matchProject.getId()){
				flag = false;
			}
		}
		if(flag){
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR
					,athlete.getName()+"不在此比赛中，请认真核对！");
		}
		//该方法有误
//		connectMapper.updateConnect(athlete.getId(), athlete.getCollegeId(), matchProject.getId(), rank, score, grade, oldAthlete.getId(), oldMatchProject.getId());
//		connectMapper.updateConnect(athlete.getStuNum(), athlete.getCollegeId(), matchProject.getId(), rank, score, grade, oldAthlete.getId(), oldMatchProject.getId());
		Connect c = new Connect();
		c.setAthletesId(athlete.getId());
		c.setRank(rank);
		c.setGrade(grade);
		c.setScore(score);
		c.setCollegeId(athlete.getCollegeId());
		c.setMatchProjectId(matchProject.getId());
		connectMapper.updateConnectData(c);

		//更新学院积分表中的积分
//		CollegeTotal collegeTotal = new CollegeTotal();
//		collegeTotal.setCollegeId(c.getCollegeId());
//		collegeTotal.setTotal(calCollegeTotal(c.getCollegeId()));
//		collegeMapper.updateTotalByCollegeId(collegeTotal);
	}

	/**
	 * 计算学院积分
	 * @param collegeId
	 * @return
	 */
	public double calCollegeTotal(int collegeId){
		//学院id对应积分
		Map<Integer,Integer> maps = new HashMap<>();
		//查出成绩表
		List<Connect> connectList = connectMapper.selectByCollegeId(collegeId);
		for(Connect connect : connectList) {
			if(connect.getAthletesId()==0 || connect.getCollegeId()==0) continue;	//跳过空数据 @lmwis
			//积分的计算
			if(maps.get(connect.getCollegeId())==null){
				maps.put(connect.getCollegeId(),connect.getScore());
			}else{
				int score = maps.get(connect.getCollegeId());
				score +=connect.getScore();
				maps.put(connect.getCollegeId(),score);
			}
		}
		return maps.get(collegeId);
	}
	/**
	 * 插入比赛成绩(名次和成绩)
	 * @param type 比赛类型
	 * @param message 信息，个人则是学号，团体则是名称
	 * @param matchProjectName 比赛
	 * @param rank 名次 
	 * @param grade 成绩
	 */
	public void insertGrade(int type, String message, String matchProjectName, int rank, int score, String grade) throws BusinessException {
		if(message == null || "".equals(message.trim())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		if(matchProjectName == null || "".equals(matchProjectName.trim())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		if(grade == null || "".equals(grade.trim())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		//找到运动员信息
//		Athletes athlete = new Athletes();//运动员信息
		Athletes athlete = null;
		athlete = judgeAthleteType(type,message);
		if(athlete==null) throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"运动员不存在");;	//空则结束
		MatchProject matchProject = matchProjectMapper.queryMatchProjectByName(matchProjectName);
		connectMapper.updateConnect(athlete.getId(), athlete.getCollegeId(), matchProject.getId(), rank, score, grade, athlete.getId(), matchProject.getId());
//		connectMapper.updateConnect(athlete.getStuNum(), athlete.getCollegeId(), matchProject.getId(), rank, score, grade, athlete.getId(), matchProject.getId());
	}
	private Athletes judgeAthleteType(Integer type, String message) throws BusinessException {
		Athletes athlete = null;
		if(type == 0) {
			athlete = athletesMapper.queryAthleteByStuNum(message);
		} else if(type == 1) {
			athlete = athletesMapper.queryTeamByName(message);
		} else {
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"运动员不存在");
		}
		return athlete;
	}

	@Autowired
	CollegeServiceV1 collegeServiceV1;
	@Override
	public void totalSyn() {
		//更新学院积分表中的积分
		for(int i=0;i<collegeServiceV1.COLLEGECOUNT;i++){
			CollegeTotal collegeTotal = new CollegeTotal();
			collegeTotal.setCollegeId(i+1);
			collegeTotal.setTotal(calCollegeTotal(i+1));
			collegeMapper.updateTotalByCollegeId(collegeTotal);
		}

	}

	/**
	 * 查询比赛排名
	 * @param matchProjectName 比赛项目编号
	 * @return 连接表集合
	 */
	public List<MatchRank> queryRank(String matchProjectName) {
		if(matchProjectName == null || "".equals(matchProjectName.trim())) {
			return null;
		}
		//查询对应比赛信息拿到比赛编号
		MatchProject matchProject = matchProjectMapper.queryMatchProjectByName(matchProjectName);
		//查询对应比赛编号的所有运动员比赛成绩
		List<Connect> connect = new ArrayList<Connect>();
		//拿到所有运动员信息
		List<Athletes> athletesList = new ArrayList<Athletes>();
		athletesList = athletesMapper.queryAthletesList();
		connect = connectMapper.queryRank(matchProject.getId());
		List<MatchRank> matchRankList = new ArrayList<MatchRank>();
		for(Connect connecttemp : connect) {
			MatchRank matchRank = new MatchRank();
			//根据运动员编号查出运动员信息
			for(Athletes athlete : athletesList) {
				if(athlete.getId() == connecttemp.getAthletesId()) {
					//找到了赋值
					matchRank.setName(athlete.getName());
					matchRank.setClassName(athlete.getClassName());
				}
			}
			matchRank.setRank(connecttemp.getRank());
			matchRank.setGrade(connecttemp.getGrade());
			matchRank.setScore(connecttemp.getScore());
			matchRankList.add(matchRank);
			}
		return matchRankList;
	}
}
