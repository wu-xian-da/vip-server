package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysCity;

public interface SysCityMapper {
    int deleteByPrimaryKey(String pid);

    int insert(SysCity record);

    int insertSelective(SysCity record);

    SysCity selectByPrimaryKey(String pid);

    int updateByPrimaryKeySelective(SysCity record);

    int updateByPrimaryKey(SysCity record);
}