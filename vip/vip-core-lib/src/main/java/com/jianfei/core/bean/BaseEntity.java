/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午3:17:50
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @Description: 公用实体类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月15日 下午3:17:50
 * 
 * @version 1.0.0
 *
 */
public class BaseEntity implements Serializable {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @version 1.0.0
	 */

	private static final long serialVersionUID = 8374127964554810557L;

	private long id;

	private String name;

	private Date createdatetime = new Date();

	private Date updatedatetime = new Date();

	private int dtflag = 0;// 0:ok 1:delete

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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatedatetime() {
		return updatedatetime;
	}

	public void setUpdatedatetime(Date updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

	/**
	 * dtflag
	 *
	 * @return the dtflag
	 * @version 1.0.0
	 */

	public int getDtflag() {
		return dtflag;
	}

	/**
	 * @param dtflag
	 *            the dtflag to set
	 */
	public void setDtflag(int dtflag) {
		this.dtflag = dtflag;
	}

}
