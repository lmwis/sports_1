package com.example.sports.service.service;


import com.example.sports.controller.viewobject.CollegePoint;

import java.util.List;

/**
 * @author Misaki
* 查询学院相关接口
 */
public interface CollegeServiceV1 {
	public final int COLLEGECOUNT = 14;

	public List<CollegePoint> getAllCollegePointAsList();

	/**
	 * 查询所有学院得分情况及奖牌数量
	 * @return 返回所有学院得分情况数组
	 */
	public CollegePoint[] getAllCollegePoint();
	/**
	 * 
	 * @param college 学院名称
	 * @return 某个学院的得分及奖牌数
	 */
	public CollegePoint getCollegePoint(String college);
	/**
	 * 
	 * @param college 学院名称
	 * @return 某个学院的得分及奖牌数
	 */
	public CollegePoint getCollegePoint2(String college);

	/**
	 * 在redis中查询院系分数
	 * @return
	 */
	public List<CollegePoint> getCollegeByRedis();
}
