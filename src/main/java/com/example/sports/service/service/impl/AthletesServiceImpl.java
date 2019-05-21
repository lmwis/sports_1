package com.example.sports.service.service.impl;

import com.example.sports.bean.*;
import com.example.sports.controller.viewobject.Athletes4QueryView;
import com.example.sports.controller.viewobject.AthletesViw;
import com.example.sports.controller.viewobject.AthletesViwForMatchProject;
import com.example.sports.controller.viewobject.TeamViw;
import com.example.sports.dao.*;
import com.example.sports.error.BusinessException;
import com.example.sports.error.EmBusinessError;
import com.example.sports.service.service.AthletesServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动员相关操作外部接口
 * @author Misaki
 *
 */
@Service
public class AthletesServiceImpl implements AthletesServiceV1 {
	@Autowired
	AthletesMapper athletesMapper;
	@Autowired
	CollegeMapper collegeMapper;
	@Autowired
	ConnectMapper connectMapper;
	@Autowired
	MatchProjectMapper matchProjectMapper;

    @Override
    public List<Athletes4QueryView> getAthletesByAthName(String name) throws BusinessException {
        Athletes athletes = athletesMapper.queryAthleteByName(name);
        if(athletes==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"远动员不存在");
        }
        List<Connect> connects = connectMapper.selectByAuthorId(athletes.getId());
        List<Athletes4QueryView> lists = new ArrayList<>();
        for(Connect c: connects){
            Athletes4QueryView athletes4QueryView = convert(c,athletes);
            if(athletes4QueryView==null) continue;
            lists.add(athletes4QueryView);
        }
        return lists;
    }

    private Athletes4QueryView convert(Connect c,Athletes athletes){
        if(c==null || c.getAthletesId()==0 || c.getCollegeId()==0||c.getMatchProjectId()==0) return null;
        Athletes4QueryView athletes4QueryView = new Athletes4QueryView();
        MatchProject matchProject = matchProjectMapper.queryMatchProjectById(c.getMatchProjectId());
        athletes4QueryView.setId(c.getAthletesId());
        athletes4QueryView.setMatch(matchProject.getName());
        athletes4QueryView.setRank(String.valueOf(c.getRank()));
        athletes4QueryView.setScore(String.valueOf(c.getScore()));
        athletes4QueryView.setGrade(c.getGrade());
        athletes4QueryView.setStuNum(athletes.getStuNum());
        athletes4QueryView.setClassName(athletes.getClassName());
        athletes4QueryView.setSex(athletes.getSex());
        athletes4QueryView.setName(athletes.getName());
        athletes4QueryView.setCollegeId(athletes.getCollegeId());
        return athletes4QueryView;
    }

    @Override
    public List<Athletes4QueryView> getAthletesByAthId(String id) throws BusinessException {
        Athletes athletes = athletesMapper.queryAthleteByStuNum(id);
        if(athletes==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"远动员不存在");
        }
        List<Connect> connects = connectMapper.selectByAuthorId(athletes.getId());

        List<Athletes4QueryView> lists = new ArrayList<>();
        for(Connect c: connects){
            Athletes4QueryView athletes4QueryView = convert(c,athletes);
            if(athletes4QueryView==null) continue;
            lists.add(athletes4QueryView);
        }
        return lists;
    }

    @Override
    public List<Athletes4QueryView> getAthletesAll() {
        List<Athletes> athletes = athletesMapper.queryAthletesList();
        List<Athletes4QueryView> lists = new ArrayList<>();

        return null;
    }

    /**
	 * 根据比赛
	 * @param name
	 * @return
	 */
	@Override
	public List<Athletes4QueryView> getAthletesByMatchName(String name) {
		List<Athletes4QueryView> lists = new ArrayList<>();
		MatchProject matchProject = matchProjectMapper.queryMatchProjectByName(name);
		List<Connect> connects = connectMapper.queryRank(matchProject.getId());
		for (Connect c:connects){
			if(c==null || c.getAthletesId()==0 || c.getCollegeId()==0||c.getMatchProjectId()==0) continue;;
			Athletes4QueryView athletes4QueryView = new Athletes4QueryView();
			athletes4QueryView.setId(c.getAthletesId());
			athletes4QueryView.setMatch(matchProject.getName());
			athletes4QueryView.setRank(String.valueOf(c.getRank()));
			athletes4QueryView.setScore(String.valueOf(c.getScore()));
			athletes4QueryView.setGrade(c.getGrade());
			Athletes athletes = athletesMapper.queryAthleteById(c.getAthletesId());
			athletes4QueryView.setStuNum(athletes.getStuNum());
			athletes4QueryView.setClassName(athletes.getClassName());
			athletes4QueryView.setSex(athletes.getSex());
			athletes4QueryView.setName(athletes.getName());
			athletes4QueryView.setCollegeId(athletes.getCollegeId());
			lists.add(athletes4QueryView);
		}
		return lists;
	}

	/**
	 * 根据学院查询运动员展示信息
	 * @param college
	 * @return
	 */
	@Override
	public List<Athletes4QueryView> getAthletesByCollege4Query(int college) {
		List<Connect> connects = connectMapper.selectByCollegeId(college);
		List<Athletes4QueryView> lists = new ArrayList<>();
		for(Connect c : connects){
			if(c==null || c.getAthletesId()==0 || c.getCollegeId()==0||c.getMatchProjectId()==0) continue;;
			Athletes4QueryView athletes4QueryView = new Athletes4QueryView();
			athletes4QueryView.setCollegeId(c.getCollegeId());
			athletes4QueryView.setGrade(c.getGrade());
			athletes4QueryView.setScore(String.valueOf(c.getScore()));
			athletes4QueryView.setRank(String.valueOf(c.getRank()));
			Athletes athletes = athletesMapper.queryAthleteById(c.getAthletesId());
			athletes4QueryView.setId(c.getAthletesId());
			athletes4QueryView.setStuNum(athletes.getStuNum());
			athletes4QueryView.setClassName(athletes.getClassName());
			athletes4QueryView.setSex(athletes.getSex());
			athletes4QueryView.setName(athletes.getName());
			athletes4QueryView.setMatch(matchProjectMapper.queryMatchProjectById(c.getMatchProjectId()).getName());
			lists.add(athletes4QueryView);
		}
		return lists;
	}

	/**
	 * 根据学院查看运动员信息
	 * @return 运动员视图表
	 */
	public List<AthletesViw> getAthletesByCollege(int college) {
		List<Athletes> athletes = athletesMapper.queryAthletesList();
		List<AthletesViw> athletesViw = new ArrayList<AthletesViw>();
		for(Athletes athlete : athletes) {
			if(athlete.getCollegeId() == college && athlete.getIsTeam() == 0) {
				AthletesViw athleteViw = new AthletesViw();
				athleteViw.setId(athlete.getId());
				athleteViw.setStuNum(athlete.getStuNum());
				athleteViw.setName(athlete.getName());
				athleteViw.setClassName(athlete.getName());
				athleteViw.setSex(athlete.getSex());
				athletesViw.add(athleteViw);
			}
		}
		return athletesViw;
	}
	/**
	 * 根据比赛项目查找运动员
	 * @param matchName 比赛项目
	 * @return 运动员视图表
	 */
	public List<AthletesViwForMatchProject> queryAthletesListByMatchProject(String matchName) {
		//拿到对应比赛的所有运动员信息表
		List<Athletes> athletes = athletesMapper.queryAthletesListByMatchProject(matchName);
		List<College> colleges = collegeMapper.queryCollegeList();
		List<AthletesViwForMatchProject> athletesViwForMatchProject = new ArrayList<AthletesViwForMatchProject>();
		//信息录入运动员视图表
		for(Athletes athlete : athletes) {
			AthletesViwForMatchProject athleteViwForMatchProject = new AthletesViwForMatchProject();
			athleteViwForMatchProject.setId(athlete.getId());
			athleteViwForMatchProject.setStuNum(athlete.getStuNum());
			athleteViwForMatchProject.setName(athlete.getName());
			athleteViwForMatchProject.setSex(athlete.getSex());
			athleteViwForMatchProject.setClassName(athlete.getClassName());
			athleteViwForMatchProject.setCollege(colleges.get(athlete.getCollegeId() - 1).getName());
			athletesViwForMatchProject.add(athleteViwForMatchProject);
		}
		return athletesViwForMatchProject;
	}
	/**
	 * 根据学院查看团体信息
	 * @return
	 */
	public List<TeamViw> getTeamByCollege(int college) {
		List<Athletes> athletes = athletesMapper.queryAthletesList();
		List<TeamViw> teamViw = new ArrayList<TeamViw>();
		for(Athletes team : athletes) {
			if(team.getCollegeId() == college && team.getIsTeam() == 1) {
				TeamViw teamViwt = new TeamViw();
				teamViwt.setId(team.getId());
				teamViwt.setName(team.getName());
				teamViwt.setSex(team.getSex());
				teamViw.add(teamViwt);
			}
		}
		return teamViw;
	}
	/**
	 * 添加运动员
	 * @param stuNum 学号
	 * @param name 姓名
	 * @param sex 性别
	 * @param collegeId 学院编号
	 */
	public void insertAthletes(String stuNum, String name, String sex, int collegeId, String className) {
		if(name == null || "".equals(name.trim())) {
			return;
		}
		if(sex == null || "".equals(sex.trim())) {
			return;
		}
		if(stuNum == null || "".equals(stuNum.trim())) {
			return;
		}
		if(className == null || "".equals(className.trim())) {
			return;
		}
		athletesMapper.insertAthletes(stuNum, name, sex, collegeId, className, 0);
	}
	/**
	 * 添加团体
	 * @param name 团体名称
	 * @param sex 团体性别
	 * @param collegeId 学院编号
	 */
	public void insertTeam(String name, String sex, int collegeId) {
		if(name == null || "".equals(name.trim())) {
			return;
		}
		if(sex == null || "".equals(sex.trim())) {
			return;
		}
		athletesMapper.insertAthletes("0", name, sex, collegeId, "NULL", 0);
	}
	/**
	 * 根据学号删除运动员
	 * @param stuNum 学号
	 */
	public void deleteAthletesByStuNum(String stuNum) {
		if(stuNum == null || "".equals(stuNum.trim())) {
			return;
		}
		athletesMapper.deleteAthletesByStuNum(stuNum);
	}
	/**
	 * 根据姓名删除运动员
	 * @param stuNum 学号
	 */
	public void deleteAthletesByName(String name) {
		if(name == null || "".equals(name.trim())) {
			return;
		}
		athletesMapper.deleteAthletesByName(name);
	}
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
	public void updateAthletesByStuNum(String StuNum, String name, String sex, int collegeId, String className, String oldStu) {
		if(name == null || "".equals(name.trim())) {
			return;
		}
		if(sex == null || "".equals(sex.trim())) {
			return;
		}
		if(StuNum == null || "".equals(StuNum.trim())) {
			return;
		}
		if(oldStu == null || "".equals(oldStu.trim())) {
			return;
		}
		if(className == null || "".equals(className.trim())) {
			return;
		}
		athletesMapper.updateAthletesByStuNum(StuNum, name, sex, collegeId, className,  oldStu);
	}

	@Override
	public void updateAthletesByStuNumV1(String StuNum, String name, int collegeId) throws BusinessException {
		if(name == null || "".equals(name.trim())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		if(StuNum == null || "".equals(StuNum.trim())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		UpdateAthletes updateAthletes = new UpdateAthletes();
		updateAthletes.setCollegeId(collegeId);
		updateAthletes.setName(name);
		athletesMapper.updateAthletesByStuNumV1(updateAthletes);
	}

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
	public void updateTeamsByName(String name, String sex, int collegeId, String oldStu) {
		if(name == null || "".equals(name.trim())) {
			return;
		}
		if(sex == null || "".equals(sex.trim())) {
			return;
		}
		if(oldStu == null || "".equals(oldStu.trim())) {
			return;
		}
		athletesMapper.updateAthletesByName("0", name, sex, collegeId, "NULL",  oldStu);
	}

}
