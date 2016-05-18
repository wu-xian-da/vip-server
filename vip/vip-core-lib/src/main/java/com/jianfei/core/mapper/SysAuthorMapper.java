package com.jianfei.core.mapper;

import com.jianfei.core.bean.SysAuthor;

public interface SysAuthorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAuthor record);

    int insertSelective(SysAuthor record);

    SysAuthor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAuthor record);

    int updateByPrimaryKey(SysAuthor record);
}