package com.jianfei.core.dto;

public class UserProvince {
	private String userId;
	private String provinceId;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}
	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserProvince [userId=" + userId + ", provinceId=" + provinceId + "]";
	}
	
}
