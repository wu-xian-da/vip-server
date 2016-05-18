package com.jianfei.core.bean;

import java.util.Date;

public class SysViproom {
    private String viproomId;

    private String airportId;

    private String viproomName;

    private Float singleconsumeMoney;

    private Integer roomNum;

    private Date openTime;

    private Date endTime;

    private String longitude;

    private String province;

    private String city;

    private String country;

    private String address;

    private String remark1;

    private Integer dtflag;

    private byte[] latitude;

    public String getViproomId() {
        return viproomId;
    }

    public void setViproomId(String viproomId) {
        this.viproomId = viproomId == null ? null : viproomId.trim();
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId == null ? null : airportId.trim();
    }

    public String getViproomName() {
        return viproomName;
    }

    public void setViproomName(String viproomName) {
        this.viproomName = viproomName == null ? null : viproomName.trim();
    }

    public Float getSingleconsumeMoney() {
        return singleconsumeMoney;
    }

    public void setSingleconsumeMoney(Float singleconsumeMoney) {
        this.singleconsumeMoney = singleconsumeMoney;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public Integer getDtflag() {
        return dtflag;
    }

    public void setDtflag(Integer dtflag) {
        this.dtflag = dtflag;
    }

    public byte[] getLatitude() {
        return latitude;
    }

    public void setLatitude(byte[] latitude) {
        this.latitude = latitude;
    }
}