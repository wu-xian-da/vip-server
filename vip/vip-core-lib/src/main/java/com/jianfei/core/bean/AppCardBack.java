package com.jianfei.core.bean;

import java.util.Date;

import com.jianfei.core.common.utils.GloabConfig;

public class AppCardBack {
	
    private String backId;//退卡流水号
    private Date createTime;//创建时间
    private int backType;//退卡方式
    private String customerName;//消费者姓名
    private String customerCard;//退款卡号
    private int dtflag;//是否逻辑删除
    private float money;//退款金额
    private Date finishTime;//完成时间
    private String orderId;//订单号
    private String createrId;//创建人编号
    private String checkId;//审核人编号
    private String agreementUrl;//协议图片
    private String cardNo;//vip卡号
    private String bankName;//银行名称
	private String customerPhone;//消费者手机号
	private float serviceMoney;//服务费
	private float safeMoney;//保险费
	private String createName;//创建人名称
	/**
	 * backId
	 *
	 * @return  the backId
	 * @version   1.0.0
	*/
	
	public String getBackId() {
		return backId;
	}
	/**
	 * @param backId the backId to set
	 */
	public void setBackId(String backId) {
		this.backId = backId;
	}
	/**
	 * createTime
	 *
	 * @return  the createTime
	 * @version   1.0.0
	*/
	
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * backType
	 *
	 * @return  the backType
	 * @version   1.0.0
	*/
	
	public int getBackType() {
		return backType;
	}
	/**
	 * @param backType the backType to set
	 */
	public void setBackType(int backType) {
		this.backType = backType;
	}
	/**
	 * customerName
	 *
	 * @return  the customerName
	 * @version   1.0.0
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
	 * customerCard
	 *
	 * @return  the customerCard
	 * @version   1.0.0
	*/
	
	public String getCustomerCard() {
		return customerCard;
	}
	/**
	 * @param customerCard the customerCard to set
	 */
	public void setCustomerCard(String customerCard) {
		this.customerCard = customerCard;
	}
	/**
	 * dtflag
	 *
	 * @return  the dtflag
	 * @version   1.0.0
	*/
	
	public int getDtflag() {
		return dtflag;
	}
	/**
	 * @param dtflag the dtflag to set
	 */
	public void setDtflag(int dtflag) {
		this.dtflag = dtflag;
	}
	/**
	 * money
	 *
	 * @return  the money
	 * @version   1.0.0
	*/
	
	public float getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(float money) {
		this.money = money;
	}
	/**
	 * finishTime
	 *
	 * @return  the finishTime
	 * @version   1.0.0
	*/
	
	public Date getFinishTime() {
		return finishTime;
	}
	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	/**
	 * orderId
	 *
	 * @return  the orderId
	 * @version   1.0.0
	*/
	
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * createrId
	 *
	 * @return  the createrId
	 * @version   1.0.0
	*/
	
	public String getCreaterId() {
		return createrId;
	}
	/**
	 * @param createrId the createrId to set
	 */
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	/**
	 * checkId
	 *
	 * @return  the checkId
	 * @version   1.0.0
	*/
	
	public String getCheckId() {
		return checkId;
	}
	/**
	 * @param checkId the checkId to set
	 */
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	/**
	 * agreementUrl
	 *
	 * @return  the agreementUrl
	 * @version   1.0.0
	*/
	
	public String getAgreementUrl() {
		return agreementUrl;
	}
	/**
	 * @param agreementUrl the agreementUrl to set
	 */
	public void setAgreementUrl(String agreementUrl) {
		this.agreementUrl = agreementUrl;
	}
	/**
	 * cardNo
	 *
	 * @return  the cardNo
	 * @version   1.0.0
	*/
	
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * bankName
	 *
	 * @return  the bankName
	 * @version   1.0.0
	*/
	
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppCardBack [backId=" + backId + ", createTime=" + createTime + ", backType=" + backType
				+ ", customerName=" + customerName + ", customerCard=" + customerCard + ", dtflag=" + dtflag
				+ ", money=" + money + ", finishTime=" + finishTime + ", orderId=" + orderId + ", createrId="
				+ createrId + ", checkId=" + checkId + ", agreementUrl=" + agreementUrl + ", cardNo=" + cardNo
				+ ", bankName=" + bankName + "]";
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public float getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(float serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public float getSafeMoney() {
		return safeMoney;
	}

	public void setSafeMoney(float safeMoney) {
		this.safeMoney = safeMoney;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}