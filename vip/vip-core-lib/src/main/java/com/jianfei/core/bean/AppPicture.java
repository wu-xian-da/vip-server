package com.jianfei.core.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class AppPicture implements Serializable {

	private static final long serialVersionUID = 4565447710159407307L;

	private Integer pictureId;

	private String viproomId;

	private String pictureUrl;

	private String clickUrl;

	private Integer priority;

	private Integer dtflag;

	private Integer imagetype;

	private String descr;

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public String getViproomId() {
		return viproomId;
	}

	public void setViproomId(String viproomId) {
		this.viproomId = viproomId == null ? null : viproomId.trim();
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl == null ? null : clickUrl.trim();
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getDtflag() {
		return dtflag;
	}

	public void setDtflag(Integer dtflag) {
		this.dtflag = dtflag;
	}

	/**
	 * imagetype
	 *
	 * @return the imagetype
	 * @version 1.0.0
	 */

	public Integer getImagetype() {
		return imagetype;
	}

	/**
	 * @param imagetype
	 *            the imagetype to set
	 */
	public void setImagetype(Integer imagetype) {
		this.imagetype = imagetype;
	}

	/**
	 * descr
	 *
	 * @return the descr
	 * @version 1.0.0
	 */

	public String getDescr() {
		return descr;
	}

	/**
	 * @param descr
	 *            the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
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