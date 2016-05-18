package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysAirport;

public interface SysAirportMapper {
    int deleteByPrimaryKey(String airportId);

    int insert(SysAirport record);

    int insertSelective(SysAirport record);

    SysAirport selectByPrimaryKey(String airportId);

    int updateByPrimaryKeySelective(SysAirport record);

    int updateByPrimaryKey(SysAirport record);
}