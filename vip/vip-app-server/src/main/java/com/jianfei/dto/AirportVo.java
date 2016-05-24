package com.jianfei.dto;

/**
 * AirportVo
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/17 14:50
 */
public class AirportVo {
    /**
     * 机场ID
     */
    private String airportId;
    /**
     * 机场Name
     */
    private String airportName;

    public AirportVo() {
    }

    public AirportVo(String airportId, String airportName) {
        this.airportId = airportId;
        this.airportName = airportName;
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
}
