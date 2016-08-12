package com.jianfei.core.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CharData {
	//开卡总数
	private String total;
	//平均开卡人数
	private String avgNum;
	//退卡数
	private String back_order_total;
	//平均退卡数
	private String avgNum_back;
	//归档日期
	private Date date;
	//业务人员数
	private String pcount;
	/**
	 * @return the avgNum_back
	 */
	public String getAvgNum_back() {
		return avgNum_back;
	}

	/**
	 * @param avgNum_back the avgNum_back to set
	 */
	public void setAvgNum_back(String avgNum_back) {
		this.avgNum_back = avgNum_back;
	}

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

	/**
	 * @return the pcount
	 */
	public String getPcount() {
		return pcount;
	}

	/**
	 * @param pcount the pcount to set
	 */
	public void setPcount(String pcount) {
		this.pcount = pcount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CharData [total=" + total + ", avgNum=" + avgNum + ", back_order_total=" + back_order_total
				+ ", avgNum_back=" + avgNum_back + ", date=" + date + ", pcount=" + pcount + "]";
	}

	
	
	
}
