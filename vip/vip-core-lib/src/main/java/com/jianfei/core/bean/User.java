/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月12日-上午11:23:34
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月12日 上午11:23:34
 * 
 * @version 1.0.0
 *
 */
public class User extends BaseEntity {

	private static final long serialVersionUID = -2817634594384609651L;

	private int state; // 0:在职，1:不在职

	private Date entryTime;// 入职时间

	private String password;

	private String loginName;

	private String salt = UUID.randomUUID().toString();

	private int userType;// 用户类型 0:业务员 1:普通后台人员 2:系统管理员

	private int sex = 0;

	private String photo;

	private boolean isLogin;

	private String phone;

	private List<Role> roles = Lists.newArrayList();

	/**
	 * state
	 *
	 * @return the state
	 * @version 1.0.0
	 */

	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * entryTime
	 *
	 * @return the entryTime
	 * @version 1.0.0
	 */

	public Date getEntryTime() {
		return entryTime;
	}

	/**
	 * @param entryTime
	 *            the entryTime to set
	 */
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	/**
	 * password
	 *
	 * @return the password
	 * @version 1.0.0
	 */

	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * loginName
	 *
	 * @return the loginName
	 * @version 1.0.0
	 */

	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * salt
	 *
	 * @return the salt
	 * @version 1.0.0
	 */

	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * userType
	 *
	 * @return the userType
	 * @version 1.0.0
	 */

	public int getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(int userType) {
		this.userType = userType;
	}

	/**
	 * sex
	 *
	 * @return the sex
	 * @version 1.0.0
	 */

	public int getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	/**
	 * photo
	 *
	 * @return the photo
	 * @version 1.0.0
	 */

	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * isLogin
	 *
	 * @return the isLogin
	 * @version 1.0.0
	 */

	public boolean isLogin() {
		return isLogin;
	}

	/**
	 * @param isLogin
	 *            the isLogin to set
	 */
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * roles
	 *
	 * @return the roles
	 * @version 1.0.0
	 */

	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * phone
	 *
	 * @return the phone
	 * @version 1.0.0
	 */

	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
		}

		return null;
	}
}
