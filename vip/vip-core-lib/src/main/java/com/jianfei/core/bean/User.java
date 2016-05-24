/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月12日-上午11:23:34
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.bean;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	private String roleIds;

	private String roelNames;
	private String ariPortIds;
	private String ariPortNames;

	private String job;

	private String code;

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
	 * code
	 *
	 * @return the code
	 * @version 1.0.0
	 */

	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * roleIds
	 *
	 * @return the roleIds
	 * @version 1.0.0
	 */

	public String getRoleIds() {
		return roleIds;
	}

	/**
	 * @param roleIds
	 *            the roleIds to set
	 */
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * roelNames
	 *
	 * @return the roelNames
	 * @version 1.0.0
	 */

	public String getRoelNames() {
		return roelNames;
	}

	/**
	 * @param roelNames
	 *            the roelNames to set
	 */
	public void setRoelNames(String roelNames) {
		this.roelNames = roelNames;
	}

	/**
	 * job
	 *
	 * @return the job
	 * @version 1.0.0
	 */

	public String getJob() {
		return job;
	}

	/**
	 * @param job
	 *            the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * ariPortIds
	 *
	 * @return the ariPortIds
	 * @version 1.0.0
	 */

	public String getAriPortIds() {
		return ariPortIds;
	}

	/**
	 * @param ariPortIds
	 *            the ariPortIds to set
	 */
	public void setAriPortIds(String ariPortIds) {
		this.ariPortIds = ariPortIds;
	}

	/**
	 * ariPortNames
	 *
	 * @return the ariPortNames
	 * @version 1.0.0
	 */

	public String getAriPortNames() {
		return ariPortNames;
	}

	/**
	 * @param ariPortNames
	 *            the ariPortNames to set
	 */
	public void setAriPortNames(String ariPortNames) {
		this.ariPortNames = ariPortNames;
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
