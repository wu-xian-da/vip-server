package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppCardBack;

public interface AppCardBackMapper {
    int deleteByPrimaryKey(String backId);

    int insert(AppCardBack record);

    int insertSelective(AppCardBack record);

    AppCardBack selectByPrimaryKey(String backId);

    int updateByPrimaryKeySelective(AppCardBack record);

    int updateByPrimaryKey(AppCardBack record);
}