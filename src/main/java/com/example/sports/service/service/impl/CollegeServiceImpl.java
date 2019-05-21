package com.example.sports.service.service.impl;

import java.util.*;

import com.example.sports.bean.College;
import com.example.sports.bean.CollegeTotal;
import com.example.sports.bean.Connect;
import com.example.sports.bean.MedalNum;
import com.example.sports.controller.viewobject.CollegePoint;
import com.example.sports.dao.CollegeDao;
import com.example.sports.dao.CollegeMapper;
import com.example.sports.dao.ConnectDao;
import com.example.sports.dao.ConnectMapper;
import com.example.sports.service.service.CollegeServiceV1;
import com.example.sports.util.RedisOperate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 学院相关操作外部接口
 * @author Misaki
 *
 */
@Service
public class CollegeServiceImpl implements CollegeServiceV1 {
	@Autowired
	CollegeMapper collegeMapper;
	@Autowired
	ConnectMapper connectMapper;

	/**
	 * 将数据转成list并按照分数排序
	 * @return
	 */
	public List<CollegePoint> getAllCollegePointAsList(){
		CollegePoint[] collegePoints =getAllCollegePoint();
		List<CollegePoint> lists = new ArrayList<>();
		for(int i=0;i<COLLEGECOUNT;i++ ){
			lists.add(collegePoints[i]);
		}
		ComparatorCollegeTotal comparatorCollegeTotal = new ComparatorCollegeTotal();
		Collections.sort(lists,comparatorCollegeTotal);
		return lists;
	}

	/**
	 * 排序内部类
	 */
	class ComparatorCollegeTotal implements Comparator {

		/**
		 * 实现内部排序算法
		 * @param o1
		 * @param o2
		 * @return
		 */
		@Override
		public int compare(Object o1, Object o2) {
			CollegePoint collegeTotal1 = (CollegePoint)o1;
			CollegePoint collegeTotal2 = (CollegePoint)o2;
			if(collegeTotal2.getTotal()>collegeTotal1.getTotal()){
				return 1;
			}else if(collegeTotal2.getTotal()==collegeTotal1.getTotal()){
				return 0;
			}else{
				return -1;
			}
		}
	}

	/**
	 * 查询所有学院得分情况及奖牌数量
	 * @return 返回所有学院得分情况数组
	 */
	public CollegePoint[] getAllCollegePoint() {
		//学院id对应积分
		Map<Integer,Integer> maps = new HashMap<>();
		//创建十三个学院的分数类数组
		CollegePoint[] collegePoint = new CollegePoint[COLLEGECOUNT];
		//查出成绩表
		List<Connect> connectList = connectMapper.queryConnectList();
		//查出学院表
		List<College> collegeList = collegeMapper.queryCollegeList();
		//建立分数信息数组
		int numOfGold[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int numOfSilver[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int numOfCopper[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int numOfGrade[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		//遍历统计各学院
		for(Connect connect : connectList) {
			if(connect.getAthletesId()==0 || connect.getCollegeId()==0) continue;	//跳过空数据 @lmwis
			if(connect.getRank() == 1)
			{
				numOfGold[connect.getCollegeId() - 1] += 1;
			}
			else if(connect.getRank() == 2)
			{
				numOfSilver[connect.getCollegeId() - 1] += 1;
			}
			else if(connect.getRank() == 3)
			{
				numOfCopper[connect.getCollegeId() - 1] += 1;
			}
			numOfGrade[connect.getCollegeId() - 1] += connect.getScore();


			//积分的计算，积分的计算迁移到数据录入时 @lmwis
//			if(maps.get(connect.getCollegeId())==null){
//				maps.put(connect.getCollegeId(),connect.getScore());
//			}else{
//				int score = maps.get(connect.getCollegeId());
//				score +=connect.getScore();
//				maps.put(connect.getCollegeId(),score);
//			}
		}
		//向分数类数组的每一个对象赋值
		int max=0;
		for(int i = 0; i < COLLEGECOUNT; i++) {
			collegePoint[i] = new CollegePoint();
			collegePoint[i].setCollegeId(collegeList.get(i).getId());
			collegePoint[i].setCollegeName(collegeList.get(i).getName());
			collegePoint[i].setGold(numOfGold[i]);
			collegePoint[i].setSilver(numOfSilver[i]);
			collegePoint[i].setCopper(numOfCopper[i]);
//			collegePoint[i].setTotal(numOfGrade[i]);
			collegePoint[i].setTotal(collegeMapper.queryCollegeTotalByCollegeId(collegePoint[i].getCollegeId()).getTotal());
//			collegeTotal.setTotal(collegePoint[i].getTotal());
//			collegeTotal.setCollegeId(collegePoint[i].getCollegeId());
//			collegeMapper.updateTotalByCollegeId(collegeTotal);
		}
		return collegePoint;
	}
	/**
	 * 
	 * @param college 学院名称
	 * @return 某个学院的得分及奖牌数
	 */
	public CollegePoint getCollegePoint(String college) {
		CollegeServiceImpl collegeService = new CollegeServiceImpl();
		CollegePoint[] collegePoint = collegeService.getAllCollegePoint();
		int i = 0;
		for(; i < COLLEGECOUNT; i++) {
			if(collegePoint[i].getCollegeName().equals(college) == true) {
				break;
			}
		}
		return collegePoint[i];
	}

	/**
	 * 
	 * @param college 学院名称
	 * @return 某个学院的得分及奖牌数
	 */
	public CollegePoint getCollegePoint2(String college) {
		CollegeServiceImpl collegeService = new CollegeServiceImpl();
		CollegePoint[] collegePoint = collegeService.getAllCollegePoint();
		int i = 0;
		for(; i < COLLEGECOUNT; i++) {
			if(collegePoint[i].getCollegeName().equals(college) == true) {
				break;
			}
		}
		return collegePoint[i];
	}

	/**
	 * 将最新数据写入redis缓存中
	 */
	@Autowired
	RedisOperate redisOperate;
	public void writeToRedis(CollegePoint[] collegePoints){
		for(int i=0;i<collegePoints.length;i++) {
			CollegePoint collegePoint = collegePoints[i];
			//写入redis
			redisOperate.hmSet("college"+collegePoint.getCollegeId(),
					"collegeName",collegePoint.getCollegeName());
			redisOperate.hmSet("college"+collegePoint.getCollegeId(),
					"gold",collegePoint.getGold());
			redisOperate.hmSet("college"+collegePoint.getCollegeId(),
					"silver",collegePoint.getSilver());
			redisOperate.hmSet("college"+collegePoint.getCollegeId(),
					"copper",collegePoint.getCopper());
			redisOperate.hmSet("college"+collegePoint.getCollegeId(),
					"total",collegePoint.getTotal());
		}

	}
	@Override
	public List<CollegePoint> getCollegeByRedis() {
		for(int i=1;i<=COLLEGECOUNT;i++){
			if(!redisOperate.exists("college"+i)){
				//获取最新数据并写入redis
				CollegePoint[] collegePoints = getAllCollegePoint();
				writeToRedis(collegePoints);
			}
		}
		List<CollegePoint> collegePoints = new ArrayList<>();
		for(int i=1;i<=COLLEGECOUNT;i++){
			CollegePoint collegePoint = new CollegePoint();
			collegePoint.setCollegeName(redisOperate.hmGet("college"+i,
					"collegeName").toString());
			collegePoint.setGold(Integer.parseInt(redisOperate.hmGet("college"+i,
					"gold").toString()));
			collegePoint.setSilver(Integer.parseInt(redisOperate.hmGet("college"+i,
					"silver").toString()));
			collegePoint.setCopper(Integer.parseInt(redisOperate.hmGet("college"+i,
					"copper").toString()));
			collegePoint.setTotalByAdmin(Integer.parseInt(redisOperate.hmGet("college"+i,
					"total").toString()));
			collegePoints.add(collegePoint);
		}

		return collegePoints;
	}
}
