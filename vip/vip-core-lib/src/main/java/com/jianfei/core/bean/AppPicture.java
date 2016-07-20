package com.jianfei.core.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jianfei.core.common.utils.GloabConfig;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AppPicture implements Serializable {

	private static final long serialVersionUID = 8175410163784326481L;
	private Integer pictureId;

	private String viproomId;

	private String pictureUrl;

	private String clickUrl;

	private Integer priority;

	@JsonIgnore
	private Integer dtflag;

	private Integer imagetype;

	private String descr;

	private Date updatetime;

	private String name;

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

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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

	/**
	 * name
	 *
	 * @return the name
	 * @version 1.0.0
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppPicture [pictureId=" + pictureId + ", viproomId="
				+ viproomId + ", pictureUrl=" + pictureUrl + ", clickUrl="
				+ clickUrl + ", priority=" + priority + ", dtflag=" + dtflag
				+ ", imagetype=" + imagetype + ", descr=" + descr
				+ ", updatetime=" + updatetime + "]";
	}

	/**
	 * 获取拼接URL
	 * 
	 * @param pictures
	 */
	public static void getStaticAdderss(List<AppPicture> pictures) {
		if (pictures == null || pictures.isEmpty())
			return;
		String url = GloabConfig.getConfig("static.resource.server.address");
		for (AppPicture picture : pictures) {
			picture.setPictureUrl(url + picture.getPictureUrl());
		}
	}

}