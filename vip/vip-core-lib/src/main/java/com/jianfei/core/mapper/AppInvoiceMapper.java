package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.common.persistence.MyBatisDao;

@MyBatisDao
public interface AppInvoiceMapper {
    int deleteByPrimaryKey(String invoiceId);

    int insert(AppInvoice record);

    int insertSelective(AppInvoice record);

    AppInvoice selectByPrimaryKey(String invoiceId);

    int updateByPrimaryKeySelective(AppInvoice record);

    int updateByPrimaryKey(AppInvoice record);
    
    //根据订单号查询发票信息
    AppInvoice selectInvoiceInfoByOrderId(String orderId);
}