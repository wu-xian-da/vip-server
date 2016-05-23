package com.jianfei.core.bean;

import java.util.Date;

public class AppVipcard {
    private String cardNo;

    private String customerId;

    private Float initMoney;

    private Float remainMoney;

    private Integer cardType;

    private Integer cardState;

    private Integer valideTime;

    private Date activeTime;
    private Date importTime;

    private String nfcId;

    private Integer dtflag;

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
	
	public Date getImportTime() {
		return importTime;
	}

	/**
	 * @param importTime the importTime to set
	 */
	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppVipcard [cardNo=" + cardNo + ", customerId=" + customerId + ", initMoney=" + initMoney
				+ ", remainMoney=" + remainMoney + ", cardType=" + cardType + ", cardState=" + cardState
				+ ", valideTime=" + valideTime + ", activeTime=" + activeTime + ", nfcId=" + nfcId + ", dtflag="
				+ dtflag + "]";
	}
    
    
}