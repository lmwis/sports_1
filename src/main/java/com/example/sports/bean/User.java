package com.example.sports.bean;
/**
 * 用户表
 * @author Misaki
 *
 */
public class User {
	/*编号*/
	private int id;
	/*用户名*/
	private String userName;
	/*密码*/
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
