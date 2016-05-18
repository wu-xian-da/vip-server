package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysRoleAuthor;

public interface SysRoleAuthorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAuthor record);

    int insertSelective(SysRoleAuthor record);

    SysRoleAuthor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAuthor record);

    int updateByPrimaryKey(SysRoleAuthor record);
}