package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysUsers;

public interface SysUsersMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUsers record);

    int insertSelective(SysUsers record);

    SysUsers selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUsers record);

    int updateByPrimaryKey(SysUsers record);
}