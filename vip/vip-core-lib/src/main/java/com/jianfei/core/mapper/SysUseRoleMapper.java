package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysUseRole;

public interface SysUseRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUseRole record);

    int insertSelective(SysUseRole record);

    SysUseRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUseRole record);

    int updateByPrimaryKey(SysUseRole record);
}