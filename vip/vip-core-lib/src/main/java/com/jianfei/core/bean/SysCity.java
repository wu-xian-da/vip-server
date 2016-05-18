package com.jianfei.core.bean;

public class SysCity {
    private String pid;

    private String cid;

    private String name;

    private Integer zipCoe;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getZipCoe() {
        return zipCoe;
    }

    public void setZipCoe(Integer zipCoe) {
        this.zipCoe = zipCoe;
    }
}