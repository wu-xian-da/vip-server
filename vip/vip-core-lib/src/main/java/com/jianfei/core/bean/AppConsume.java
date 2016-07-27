package com.jianfei.core.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppConsume {
    private String consumeId;

    private String cardNo;

    private Float consumeMoney;

    private Date consumeTime;

    private String viproomName;

    private Integer dtflag;

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId == null ? null : consumeId.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public Float getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(Float consumeMoney) {
        this.consumeMoney = consumeMoney;
    }

    public Date getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Date consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getViproomName() {
        return viproomName;
    }

    public void setViproomName(String viproomName) {
        this.viproomName = viproomName == null ? null : viproomName.trim();
    }

    public Integer getDtflag() {
        return dtflag;
    }

    public void setDtflag(Integer dtflag) {
        this.dtflag = dtflag;
    }
}