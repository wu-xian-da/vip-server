package com.jianfei.core.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CharData {
	//该省份当日总的开卡总数
	private String total;
	//该省份当日业务人员平均开卡人数
	private String avgNum;
	
	
	private Date date;

	/**
	 * @return the date
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the avgNum
	 */
	public String getAvgNum() {
		return avgNum;
	}

	/**
	 * @param avgNum the avgNum to set
	 */
	public void setAvgNum(String avgNum) {
		this.avgNum = avgNum;
	}
	
}
