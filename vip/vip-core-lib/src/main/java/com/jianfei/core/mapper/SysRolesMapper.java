package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysRoles;

public interface SysRolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoles record);

    int insertSelective(SysRoles record);

    SysRoles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoles record);

    int updateByPrimaryKey(SysRoles record);
}