/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月12日-上午11:27:12
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.bean;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月12日 上午11:27:12
 * 
 * @version 1.0.0
 *
 */
public class Role extends BaseEntity {

	private static final long serialVersionUID = -6573492166850318322L;

	private String description;

	private List<Resource> resources = Lists.newArrayList(); // 拥有菜单列表


	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
