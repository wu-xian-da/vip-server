package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppAttendance;

public interface AppAttendanceMapper {
    int deleteByPrimaryKey(Long chckId);

    int insert(AppAttendance record);

    int insertSelective(AppAttendance record);

    AppAttendance selectByPrimaryKey(Long chckId);

    int updateByPrimaryKeySelective(AppAttendance record);

    int updateByPrimaryKey(AppAttendance record);
}