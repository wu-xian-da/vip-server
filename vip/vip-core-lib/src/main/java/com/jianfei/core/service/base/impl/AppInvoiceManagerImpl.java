package com.jianfei.core.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.mapper.AppInvoiceMapper;
import com.jianfei.core.service.base.AppInvoiceManager;

@Service
public class AppInvoiceManagerImpl implements AppInvoiceManager{
	@Autowired
	private AppInvoiceMapper appInvoiceMapper;
	/**
	 * 根据订单编号返回发票信息
	 */
	@Override
	public AppInvoice selInvoiceInfoByOrderId(String orderId) {
		
		return appInvoiceMapper.selectInvoiceInfoByOrderId(orderId);
	}

}
