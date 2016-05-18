package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysUserAirport;

public interface SysUserAirportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserAirport record);

    int insertSelective(SysUserAirport record);

    SysUserAirport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserAirport record);

    int updateByPrimaryKey(SysUserAirport record);
}