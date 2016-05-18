package com.jianfei.core.bean;

import java.util.Date;

public class AppAttendance {
    private Long chckId;

    private String agentId;

    private Date checkTime;

    private Integer valideCheck;

    private Integer dflag;

    public Long getChckId() {
        return chckId;
    }

    public void setChckId(Long chckId) {
        this.chckId = chckId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getValideCheck() {
        return valideCheck;
    }

    public void setValideCheck(Integer valideCheck) {
        this.valideCheck = valideCheck;
    }

    public Integer getDflag() {
        return dflag;
    }

    public void setDflag(Integer dflag) {
        this.dflag = dflag;
    }
}