package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysViproom;

public interface SysViproomMapper {
    int deleteByPrimaryKey(String viproomId);

    int insert(SysViproom record);

    int insertSelective(SysViproom record);

    SysViproom selectByPrimaryKey(String viproomId);

    int updateByPrimaryKeySelective(SysViproom record);

    int updateByPrimaryKeyWithBLOBs(SysViproom record);

    int updateByPrimaryKey(SysViproom record);
}