package com.example.sports.service.service;

import com.example.sports.bean.MatchProject;
import com.example.sports.controller.viewobject.MatchProjectViw;
import com.example.sports.pojo.Match;

import java.util.List;
import java.util.Map;


/**
 * 比赛项目相关操作接口
 * @author Misaki
 *
 */
public interface MatchProjectServiceV1 {
	/**
	 * 插入比赛项目
	 * @param name 项目名称
	 * @param type 项目类型
	 * @param time 时间
	 */
	public void insertMatchProject(String name, int type, String time);
	/**
	 * 根据比赛名称删除比赛项目
	 * @param name 比赛名称
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
	 * @return 比赛项目集合(名称，类型，时间)
	 */
	public List<MatchProjectViw> queryMatchProjectList();
	/**
	 * 查询所有个人比赛项目
	 * @return 比赛项目集合(名称，类型，时间)
	 */
	public List<MatchProjectViw> queryIndividualMatchProjectList();
	/**
	 * 查询所有团体比赛项目
	 * @return 比赛项目集合(名称，类型，时间)
	 */
	public List<MatchProjectViw> queryTeamlMatchProjectList();

	/**
	 * 分页查询
	 * 	查询数据
	 * 	@lmwis
	 * @param map
	 * @return
	 */
	public List<MatchProject> queryData(Map<String,Object> map);

	/**
	 * 分页查询
	 * 	查询页数
	 * 	@lmwis
	 * @param map
	 * @return
	 */
	public Integer getCount(Map<String,Object> map);
}
