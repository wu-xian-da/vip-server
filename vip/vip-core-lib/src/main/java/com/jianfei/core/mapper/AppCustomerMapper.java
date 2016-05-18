package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppCustomer;

public interface AppCustomerMapper {
    int deleteByPrimaryKey(String customerId);

    int insert(AppCustomer record);

    int insertSelective(AppCustomer record);

    AppCustomer selectByPrimaryKey(String customerId);

    int updateByPrimaryKeySelective(AppCustomer record);

    int updateByPrimaryKey(AppCustomer record);
}