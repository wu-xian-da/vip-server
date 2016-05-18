package com.jianfei.core.bean;

public class SysAirport {
    private String airportId;

    private String airportName;

    private Integer state;

    private Integer agentNum;

    private String province;

    private String city;

    private String country;

    private String address;

    private String longitude;

    private String latitude;

    private Integer dtflag;

    private String headerName;

    private String headerPhone;

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId == null ? null : airportId.trim();
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName == null ? null : airportName.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAgentNum() {
        return agentNum;
    }

    public void setAgentNum(Integer agentNum) {
        this.agentNum = agentNum;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public Integer getDtflag() {
        return dtflag;
    }

    public void setDtflag(Integer dtflag) {
        this.dtflag = dtflag;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName == null ? null : headerName.trim();
    }

    public String getHeaderPhone() {
        return headerPhone;
    }

    public void setHeaderPhone(String headerPhone) {
        this.headerPhone = headerPhone == null ? null : headerPhone.trim();
    }
}