package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppConsume;

public interface AppConsumeMapper {
    int deleteByPrimaryKey(String consumeId);

    int insert(AppConsume record);

    int insertSelective(AppConsume record);

    AppConsume selectByPrimaryKey(String consumeId);

    int updateByPrimaryKeySelective(AppConsume record);

    int updateByPrimaryKey(AppConsume record);
}