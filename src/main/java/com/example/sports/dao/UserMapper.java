package com.example.sports.dao;

import com.example.sports.bean.User;

import java.util.List;

/**
 * @author lmwis on 2019-04-20 21:57
 */
public interface UserMapper {
    /**
     * 查询管理员用户表
     * @return 返回管理员用户表
     */
    public List<User> queryUserList();
}
