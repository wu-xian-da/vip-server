package com.jianfei.core.service.base.impl;

import com.jianfei.core.common.utils.IdGen;
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

	/**
	 * 添加发票信息
	 *
	 * @param appInvoice
	 * @return
	 */
	@Override
	public boolean insert(AppInvoice appInvoice) {
		appInvoice.setInvoiceId(IdGen.uuid());
		return appInvoiceMapper.insert(appInvoice) == 1 ? true : false;
	}
	
	/**
	 * 更新发票信息
	 */
	@Override
	public int updateByPrimaryKeySelective(AppInvoice appInvoice) {
		// TODO Auto-generated method stub
		return appInvoiceMapper.updateByPrimaryKeySelective(appInvoice);
	}
}
