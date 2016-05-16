/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月13日-上午12:02:00
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.util.List;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com 
 * @date: 2016年5月13日 上午12:02:00 
 * 
 * @version 1.0.0
 *
 */
/**
 * 树 json model 数据
 * 
 * @author glq
 *
 */
public class JsonTreeData {

	public String id;// json id
	public String pid;//
	public String name;// json 显示文本
	public String text;// json 显示文本
	public String state;// json 'open','closed'
	public List<JsonTreeData> children;//

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<JsonTreeData> getChildren() {
		return children;
	}

	public void setChildren(List<JsonTreeData> children) {
		this.children = children;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}