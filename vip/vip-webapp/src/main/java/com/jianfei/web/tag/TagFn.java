/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月3日-上午11:36:35
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.web.tag;

import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.tagext.TagSupport;

import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.enu.PostType;

/**
 *
 * @Description: 自定义标签
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月3日 上午11:36:35
 * 
 * @version 1.0.0
 *
 */
public class TagFn extends TagSupport {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @version 1.0.0
	 */

	private static final long serialVersionUID = 8002410789975827590L;

	public static String encode(String url) {
		try {
			return java.net.URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return url == null ? "" : url;
		}
	}

	public static String decode(String url) {
		try {
			return java.net.URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return url == null ? "" : url;
		}
	}

	public static String payType(Object obj) {
		if (null != obj) {
			if (PayType.ALIPAY.equals(obj.toString())) {
				return "支付宝";
			} else if (PayType.BANKPAY.equals(obj.toString())) {
				return "银行卡刷卡";
			} else if (PayType.CASHPAY.equals(obj.toString())) {
				return "现金支付";
			} else if (PayType.WXPAY.equals(obj.toString())) {
				return "微信支付";
			} else {
				return obj.toString();
			}
		}

		return "";
	}

	public static String postType(Object obj) {
		if (null != obj) {
			if (PostType.companyType.getType().equals(obj.toString())) {
				return PostType.companyType.getValue();
			} else if (PostType.personType.getType().equals(obj.toString())) {
				return PostType.personType.getValue();
			} else {
				return obj.toString();
			}
		}
		return "";
	}
}
