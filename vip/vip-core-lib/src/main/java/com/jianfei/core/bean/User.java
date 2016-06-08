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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jianfei.core.common.utils.GloabConfig;

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

	private Integer state; // 0:在职，1:不在职

	private Date entryTime;// 入职时间

	@JsonIgnore
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
	private String roleTypes;

	private String job;

	private String code;// 角色
	// 角色集合
	private List<Role> roles = Lists.newArrayList();
	// 数据权限
	private List<AriPort> aripors = Lists.newArrayList();

	private String extraPasswd;// 没有加盐，md5加密后的密码

	/**
	 * state
	 *
	 * @return the state
	 * @version 1.0.0
	 */

	public Integer getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(Integer state) {
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

	public String getExtraPasswd() {
		return extraPasswd;
	}

	public void setExtraPasswd(String extraPasswd) {
		this.extraPasswd = extraPasswd;
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

	public String getRoleTypes() {
		return roleTypes;
	}

	public void setRoleTypes(String roleTypes) {
		this.roleTypes = roleTypes;
	}

	/**
	 * photo
	 *
	 * @return the photo
	 * @version 1.0.0
	 */

	public String getPhoto() {
		return photo == null ? null : GloabConfig
				.getConfig("static.resource.server.address") + photo;
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
	 * aripors
	 *
	 * @return the aripors
	 * @version 1.0.0
	 */
	@JsonIgnore
	public List<AriPort> getAripors() {
		return aripors;
	}

	/**
	 * @param aripors
	 *            the aripors to set
	 */
	public void setAripors(List<AriPort> aripors) {
		this.aripors = aripors;
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
