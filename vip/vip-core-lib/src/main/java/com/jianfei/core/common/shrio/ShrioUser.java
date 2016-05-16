package com.jianfei.core.common.shrio;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
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

	private static final long serialVersionUID = 7760388468366884626L;

	private List<String> permission = Lists.newArrayList();

	private User user;

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
