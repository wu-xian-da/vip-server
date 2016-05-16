package com.jianfei.core.common.shrio;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jianfei.core.bean.MenBuilder;
import com.jianfei.core.bean.User;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月15日 下午3:02:55
 * 
 * @version 1.0.0
 *
 */
public class ShrioUser implements Serializable {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @version 1.0.0
	 */

	private static final long serialVersionUID = -6957137697772659128L;

	private long id;

	private String name;

	private String loginName;

	private List<String> permission = Lists.newArrayList();

	private List<MenBuilder> menus = Lists.newArrayList();

	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<String> getPermission() {
		return permission;
	}

	public void setPermission(List<String> permission) {
		this.permission = permission;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<MenBuilder> getMenus() {
		return menus;
	}

	public void setMenus(List<MenBuilder> menus) {
		this.menus = menus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
