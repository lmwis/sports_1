package com.example.sports.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.sports.bean.User;
import com.example.sports.controller.viewobject.UserViw;
import com.example.sports.dao.UserDao;
import com.example.sports.dao.UserMapper;
import com.example.sports.error.BusinessException;
import com.example.sports.error.EmBusinessError;
import com.example.sports.service.service.UserServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户表相关操作接口实现
 * @author Misaki
 *
 */
@Service
public class UserServiceImpl implements UserServiceV1 {
	@Autowired
	UserMapper userMapper;
	/**
	 * 查询管理员用户表
	 * @return 返回管理员用户表
	 * @throws BusinessException 用户不存在
	 */
	public List<UserViw> queryUserList() throws BusinessException {
		List<User> userList = userMapper.queryUserList();
		if(userList.isEmpty() == true)
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
		List<UserViw> userViwList = new ArrayList<UserViw>();
		for(User us : userList) {
			UserViw userViw = new UserViw();
			userViw.setUserName(us.getUserName());
			userViw.setPassword(us.getPassword());
			userViwList.add(userViw);
		}
		if(userViwList.isEmpty()) {
			return null;
		}
		return userViwList;
	}

	@Override
	public boolean login(String username, String password) throws BusinessException {
		List<UserViw> userList = this.queryUserList();
		for(UserViw userViw : userList){
			if(username.equals(userViw.getUserName()) && password.equals(userViw.getPassword())){
				return true;
			}
		}
		return false;
	}
}
