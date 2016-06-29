package com.jianfei.core.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CharData {
	//个人开卡总数
	private String total;
	//平均开卡人数
	private String avgNum;
	//退卡数
	private String back_order_total;
	
	
	/**
	 * @return the back_order_total
	 */
	public String getBack_order_total() {
		return back_order_total;
	}

	/**
	 * @param back_order_total the back_order_total to set
	 */
	public void setBack_order_total(String back_order_total) {
		this.back_order_total = back_order_total;
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CharData [total=" + total + ", avgNum=" + avgNum + ", back_order_total=" + back_order_total + ", date="
				+ date + "]";
	}
	
	
}
