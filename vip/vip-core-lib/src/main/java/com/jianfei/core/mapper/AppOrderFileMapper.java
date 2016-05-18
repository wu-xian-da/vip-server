package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppOrderFile;

public interface AppOrderFileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(AppOrderFile record);

    int insertSelective(AppOrderFile record);

    AppOrderFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(AppOrderFile record);

    int updateByPrimaryKey(AppOrderFile record);
}