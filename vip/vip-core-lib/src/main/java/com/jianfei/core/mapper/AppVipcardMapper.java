package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppVipcard;

public interface AppVipcardMapper {
    int deleteByPrimaryKey(String cardNo);

    int insert(AppVipcard record);

    int insertSelective(AppVipcard record);

    AppVipcard selectByPrimaryKey(String cardNo);

    int updateByPrimaryKeySelective(AppVipcard record);

    int updateByPrimaryKey(AppVipcard record);
}