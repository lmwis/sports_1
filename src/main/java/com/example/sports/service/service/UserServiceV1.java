package com.example.sports.service.service;

import com.example.sports.controller.viewobject.UserViw;
import com.example.sports.error.BusinessException;

import java.util.List;

public interface UserServiceV1 {
    public List<UserViw> queryUserList() throws BusinessException;
    public boolean login(String username, String password) throws BusinessException;
}
