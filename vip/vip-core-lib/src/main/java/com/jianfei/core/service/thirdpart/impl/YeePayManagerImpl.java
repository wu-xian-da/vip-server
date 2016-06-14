package com.jianfei.core.service.thirdpart.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.jianfei.core.common.pay.PayNotifyRequest;
import com.jianfei.core.common.pay.PayQueryResult;
import com.jianfei.core.common.pay.PreCreateResult;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.YeepayUtils;
import com.jianfei.core.common.utils.impl.HttpServiceRequest;
import com.jianfei.core.service.thirdpart.ThirdPayManager;

@Service("yeepayManager")
public class YeePayManagerImpl extends ThirdPayManager {

	@Override
	public PreCreateResult tradePrecreate(Object payParam) {
		// TODO Auto-generated method stub
		return null;
	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	public PayQueryResult tradeQuery(String tradeNo) {
		
    	Document   document   = DocumentHelper.createDocument(); 
    	Element codmsEle =  document.addElement("COD-MS");
    	Element headEle = codmsEle.addElement("SessionHead");
    	Element verEle = headEle.addElement("Version");
    	verEle.setText("V1.0");
    	Element serviceCodeEle = headEle.addElement("ServiceCode");
    	serviceCodeEle.setText("COD414");
    	Element tranIdEle = headEle.addElement("TransactionID");
    	
    	tranIdEle.setText(YeepayUtils.genTranctionId());
    	//tranIdEle.setText("ddddddddCOD414201606077628111915");
    	Element srcSysIDEle = headEle.addElement("SrcSysID");
    	srcSysIDEle.setText(GloabConfig.getConfig("yeepay.src.sysid"));
    	Element dstSysIDEle = headEle.addElement("DstSysID");
    	dstSysIDEle.setText(GloabConfig.getConfig("yeepay.dst.sysid"));
    	Element respTimeEle = headEle.addElement("Resp_Time");
    	
    	respTimeEle.setText(sdf.format(new Date()));
    	//respTimeEle.setText("20160607144433");
    	Element hmacEle = headEle.addElement("HMAC");
    	hmacEle.setText("hmac");
    	
    	Element bodyEle = codmsEle.addElement("SessionBody");
    	Element orderNoEle = bodyEle.addElement("OrderNo");
    	orderNoEle.setText(tradeNo);
    	Element customerNoEle = bodyEle.addElement("CustomerNo");
    	customerNoEle.setText(GloabConfig.getConfig("yeepay.customerId"));
    	System.out.println(document.asXML());
		String request =  YeepayUtils.hmacSign(document.asXML(), GloabConfig.getConfig("yeepay.key"));
		String result = HttpServiceRequest.getInstance().sendPostXml(GloabConfig.getConfig("yeepay.query.url"), request);
		PayQueryResult payQueryResult = new PayQueryResult();
		payQueryResult.setTradeNo(tradeNo);
		Document documentR;
		try {
			documentR = DocumentHelper.parseText(result);
			Element root = documentR.getRootElement();
			Element sessionHead = root.element("SessionHead");
			Element sessionBody = root.element("SessionBody");
			String serviceCode = sessionHead.elementText("ServiceCode");
			String resultCode = sessionHead.elementText("ResultCode");
		
			if (resultCode.equals("2")){
				String payStatus = sessionBody.elementText("PayStatus");
				if (payStatus.equals("0")){
					payQueryResult.setCode("0");
					payQueryResult.setMsg("已支付");
				}else if (payStatus.equals("1")){
					payQueryResult.setCode("1");
					payQueryResult.setMsg("未支付");
				}else if (payStatus.equals("2")){
					payQueryResult.setCode("2");
					payQueryResult.setMsg("拆单未完全支付");
				}else if (payStatus.equals("3")){
					payQueryResult.setCode("3");
					payQueryResult.setMsg("撤销");
				}else if (payStatus.equals("4")){
					payQueryResult.setCode("4");
					payQueryResult.setMsg("已退款");
				}else if (payStatus.equals("5")){
					payQueryResult.setCode("5");
					payQueryResult.setMsg("已授权");
				}else{
					payQueryResult.setCode("-1");
					payQueryResult.setMsg("未知状态");
				}
			}else{
				payQueryResult.setCode("-1");
				payQueryResult.setMsg("未知状态");
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
			payQueryResult.setCode("-1");
			payQueryResult.setMsg("未知状态");
			return payQueryResult;
		}
		return payQueryResult;
	}

	@Override
	public void tradeRefund(String tradeNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public String payNotify(PayNotifyRequest param) {
		// TODO Auto-generated method stub
		return null;
	}

}
