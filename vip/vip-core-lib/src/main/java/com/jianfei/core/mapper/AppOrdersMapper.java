package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppOrders;

public interface AppOrdersMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(AppOrders record);

    int insertSelective(AppOrders record);

    AppOrders selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(AppOrders record);

    int updateByPrimaryKey(AppOrders record);
}