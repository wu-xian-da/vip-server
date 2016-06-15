package com.jianfei.core.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public  class YeepayUtils {
	/**
	 * 易宝支付签名算法
	* @param srcXml 原 xml
	* @param secKey 加密key
	* @return 生成新的带hamc节点的xml */
	public static String hmacSign(String srcXml, String secKey) {
		// xml 文档开始与结束部分,此部分不进行加密处理,在拼接时使用
		String xmlNodeStartString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COD-MS>";
		String xmlNodeEndString = "</COD-MS>";
		// 需要加密的字符串 String md5String = "";
		// 本次传送 xml 的 md5
		String md5token = "";
		String md5String = srcXml.substring(srcXml.indexOf("<COD-MS>") + 8, srcXml.indexOf("</COD-MS>"));
		// 把 hmac 节点过滤点
		String hmac = srcXml.substring(srcXml.indexOf("<HMAC>"),srcXml.indexOf("</HMAC>") + 7);
		md5String = md5String.replace(hmac, "");
		md5String = filter(md5String);
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);
		md5token = md5.encodePassword(md5String + secKey, null);
		String startStr = md5String.split("</SessionHead>")[0];
		String endStr="";
		if (md5String.split("</SessionHead>").length > 1){
			//hmac节点后部分
			endStr = md5String.split("</SessionHead>")[1];
		}
		return xmlNodeStartString + startStr + "<HMAC>" + md5token + "</HMAC>" +"</SessionHead>" + endStr + xmlNodeEndString;
	}
	
	/**
	 * 获取签名的内容
	 * @param srcXml
	 * @param secKey
	 * @return
	 */
	public static String getSignField(String srcXml, String secKey) {
		// 需要加密的字符串 String md5String = "";
		// 本次传送 xml 的 md5
		String md5token = "";
		String md5String = srcXml.substring(srcXml.indexOf("<COD-MS>") + 8, srcXml.indexOf("</COD-MS>"));
		// 把 hmac 节点过滤点
		String hmac = srcXml.substring(srcXml.indexOf("<HMAC>"),srcXml.indexOf("</HMAC>") + 7);
		md5String = md5String.replace(hmac, "");
		md5String = filter(md5String);
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);
		md5token = md5.encodePassword(md5String + secKey, null);
		return md5token;
	}
	
	public static String filter(String content) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(content);
		return m.replaceAll("");
	}
	

    
    public static String genTranctionId(){
    	return GloabConfig.getConfig("yeepay.transaction.id")+"COD414"+sdf.format(new Date())+UUIDUtils.getRandomStringByLength(10);
    }
    
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
}
