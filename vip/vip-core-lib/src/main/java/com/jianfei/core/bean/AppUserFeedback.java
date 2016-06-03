package com.jianfei.core.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户反馈实体类
 * @author guojian
 *
 */
public class AppUserFeedback {
	private Integer id;//反馈编号
	private String userId;//用户编号
	private String customerName;//用户姓名
	private String customerPhone;//用户手机号码
	private Date feedbackTime;//反馈时间
	private Integer feedbackState;//处理状态 0 为处理  1已处理
	private String feedbackStateName;//处理状态的中文名称
	private Date handleTime;//处理时间
	private String handleuserId;//处理人编号
	private Integer dtflag;//0未删除 1已删除
	private String opr;//操作选项
	private String feedbackContent;//反馈详情
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
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
	 * @return the feedbackTime
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getFeedbackTime() {
		return feedbackTime;
	}
	/**
	 * @param feedbackTime the feedbackTime to set
	 */
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	/**
	 * @return the feedbackState
	 */
	public Integer getFeedbackState() {
		return feedbackState;
	}
	/**
	 * @param feedbackState the feedbackState to set
	 */
	public void setFeedbackState(Integer feedbackState) {
		this.feedbackState = feedbackState;
	}
	/**
	 * @return the handleTime
	 */
	public Date getHandleTime() {
		return handleTime;
	}
	/**
	 * @param handleTime the handleTime to set
	 */
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	/**
	 * @return the handleuserId
	 */
	public String getHandleuserId() {
		return handleuserId;
	}
	/**
	 * @param handleuserId the handleuserId to set
	 */
	public void setHandleuserId(String handleuserId) {
		this.handleuserId = handleuserId;
	}
	/**
	 * @return the dtflag
	 */
	public Integer getDtflag() {
		return dtflag;
	}
	/**
	 * @param dtflag the dtflag to set
	 */
	public void setDtflag(Integer dtflag) {
		this.dtflag = dtflag;
	}
	
	
	
	/**
	 * @return the opr
	 */
	public String getOpr() {
		return opr;
	}
	/**
	 * @param opr the opr to set
	 */
	public void setOpr(String opr) {
		this.opr = opr;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}
	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	
	/**
	 * @return the feedbackStateName
	 */
	public String getFeedbackStateName() {
		return feedbackStateName;
	}
	/**
	 * @param feedbackStateName the feedbackStateName to set
	 */
	public void setFeedbackStateName(String feedbackStateName) {
		this.feedbackStateName = feedbackStateName;
	}
	/**
	 * @return the feedbackContent
	 */
	public String getFeedbackContent() {
		return feedbackContent;
	}
	/**
	 * @param feedbackContent the feedbackContent to set
	 */
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppUserFeedback [id=" + id + ", userId=" + userId + ", customerName=" + customerName
				+ ", customerPhone=" + customerPhone + ", feedbackTime=" + feedbackTime + ", feedbackState="
				+ feedbackState + ", feedbackStateName=" + feedbackStateName + ", handleTime=" + handleTime
				+ ", handleuserId=" + handleuserId + ", dtflag=" + dtflag + ", opr=" + opr + ", feedbackContent="
				+ feedbackContent + "]";
	}
	
	
	
	
	
	
	
	
	
}
