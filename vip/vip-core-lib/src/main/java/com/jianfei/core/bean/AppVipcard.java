package com.jianfei.core.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class AppVipcard {
    private String cardNo;

    private String customerId;

    private Float initMoney;

    private Float remainMoney;

    private Integer cardType;

    private Integer cardState;

    private Integer valideTime;

    private Date activeTime;

    private Date expiryTime;
    private Date importTime;

    private String nfcId;

    private String cardName;

    private Integer dtflag;

    /**
     * 销售时间
     */
    private Date saleTime;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public Float getInitMoney() {
        return initMoney;
    }

    public void setInitMoney(Float initMoney) {
        this.initMoney = initMoney;
    }

    public Float getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(Float remainMoney) {
        this.remainMoney = remainMoney;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getCardState() {
        return cardState;
    }

    public void setCardState(Integer cardState) {
        this.cardState = cardState;
    }

    public Integer getValideTime() {
        return valideTime;
    }

    public void setValideTime(Integer valideTime) {
        this.valideTime = valideTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public String getNfcId() {
        return nfcId;
    }

    public void setNfcId(String nfcId) {
        this.nfcId = nfcId == null ? null : nfcId.trim();
    }

    public Integer getDtflag() {
        return dtflag;
    }

    public void setDtflag(Integer dtflag) {
        this.dtflag = dtflag;
    }
    

	/**
	 * importTime
	 *
	 * @return  the importTime
	 * @version   1.0.0
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getImportTime() {
		return importTime;
	}

	/**
	 * @param importTime the importTime to set
	 */
	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return "AppVipcard [cardNo=" + cardNo + ", customerId=" + customerId + ", initMoney=" + initMoney
				+ ", remainMoney=" + remainMoney + ", cardType=" + cardType + ", cardState=" + cardState
				+ ", valideTime=" + valideTime + ", activeTime=" + activeTime + ", importTime=" + importTime
				+ ", nfcId=" + nfcId + ", dtflag=" + dtflag + "]";
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }
}