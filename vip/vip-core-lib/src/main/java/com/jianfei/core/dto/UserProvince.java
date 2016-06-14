package com.jianfei.core.dto;

public class UserProvince {
	private String userId;
	private String provinceId;
	private String airPortId;
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
	/**
	 * @return the airPortId
	 */
	public String getAirPortId() {
		return airPortId;
	}
	/**
	 * @param airPortId the airPortId to set
	 */
	public void setAirPortId(String airPortId) {
		this.airPortId = airPortId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserProvince [userId=" + userId + ", provinceId=" + provinceId + ", airPortId=" + airPortId + "]";
	}
	
	
}
