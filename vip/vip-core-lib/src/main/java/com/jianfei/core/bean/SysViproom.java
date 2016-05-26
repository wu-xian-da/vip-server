package com.jianfei.core.bean;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SysViproom {
    private String viproomId;//编号

    private String airportId;//场站编号
    
    private String airportName;//场站名称
    
    private String headerPhone;//负责人电话号码
    private String headerName;//场站负责人

    private String viproomName;//名称

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

    private String latitude;

    private List<AppPicture> pictures= Lists.newArrayList();

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

	/**
	 * airportName
	 *
	 * @return  the airportName
	 * @version   1.0.0
	*/
	
	public String getAirportName() {
		return airportName;
	}

	/**
	 * @param airportName the airportName to set
	 */
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	/**
	 * headerPhone
	 *
	 * @return  the headerPhone
	 * @version   1.0.0
	*/
	
	public String getHeaderPhone() {
		return headerPhone;
	}

	/**
	 * @param headerPhone the headerPhone to set
	 */
	public void setHeaderPhone(String headerPhone) {
		this.headerPhone = headerPhone;
	}

	/**
	 * headerName
	 *
	 * @return  the headerName
	 * @version   1.0.0
	*/
	
	public String getHeaderName() {
		return headerName;
	}

	/**
	 * @param headerName the headerName to set
	 */
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SysViproom [viproomId=" + viproomId + ", airportId=" + airportId + ", airportName=" + airportName
				+ ", headerPhone=" + headerPhone + ", headerName=" + headerName + ", viproomName=" + viproomName
				+ ", singleconsumeMoney=" + singleconsumeMoney + ", roomNum=" + roomNum + ", openTime=" + openTime
				+ ", endTime=" + endTime + ", longitude=" + longitude + ", province=" + province + ", city=" + city
				+ ", country=" + country + ", address=" + address + ", remark1=" + remark1 + ", dtflag=" + dtflag
				+ ", latitude=" + latitude + "]";
	}

    public List<AppPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<AppPicture> pictures) {
        this.pictures = pictures;
    }
}