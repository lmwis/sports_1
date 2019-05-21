package com.example.sports.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.sports.bean.User;
import com.example.sports.db.DBAccess;
import org.apache.ibatis.session.SqlSession;


/**
 * 用户表操作
 * @author Misaki
 *
 */
public class UserDao {
	/**
	 * 查询管理员用户表
	 * @return 返回管理员用户表
	 */
	public List<User> queryUserList() {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		List<User> userList = new ArrayList<User>();
		try {
			sqlSession = dbAccess.getSqlSession();
			userList = sqlSession.selectList("User.queryUserList");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		if(userList.isEmpty()) {
			return null;
		}
		return userList;
	}


}
