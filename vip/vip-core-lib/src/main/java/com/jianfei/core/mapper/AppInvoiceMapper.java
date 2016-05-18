package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppInvoice;

public interface AppInvoiceMapper {
    int deleteByPrimaryKey(String invoiceId);

    int insert(AppInvoice record);

    int insertSelective(AppInvoice record);

    AppInvoice selectByPrimaryKey(String invoiceId);

    int updateByPrimaryKeySelective(AppInvoice record);

    int updateByPrimaryKey(AppInvoice record);
}