package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.persistence.MyBatisDao;
import com.sun.tools.javac.util.List;

@MyBatisDao
public interface SysViproomMapper {
    int deleteByPrimaryKey(String viproomId);

    int insert(SysViproom record);

    int insertSelective(SysViproom record);

    SysViproom selectByPrimaryKey(String viproomId);

    int updateByPrimaryKeySelective(SysViproom record);

    int updateByPrimaryKeyWithBLOBs(SysViproom record);

    int updateByPrimaryKey(SysViproom record);

    List<SysViproom> getVipRoomList(SysAirport airport);
}