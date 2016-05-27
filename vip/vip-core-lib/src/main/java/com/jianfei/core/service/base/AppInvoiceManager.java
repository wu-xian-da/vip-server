package com.jianfei.core.service.base;

import com.jianfei.core.bean.AppInvoice;

public interface AppInvoiceManager {
	//根据订单号返回发票信息
	public AppInvoice selInvoiceInfoByOrderId(String orderId);
}
