/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午12:47:27
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.cache;

import com.jianfei.core.common.enu.MsgType;

/**
 *
 * @Description: 缓存常量
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午12:47:27
 * 
 * @version 1.0.0
 *
 */
public class CacheCons {

	public interface Sys {
		public static final String SYS_ROLE_LIST = "sys:role:list";
		public static final String SYS_RESOURCE_LIST = "sys_resource_list";

		/**
		 * last_1_month:TODO（按照省统计上个月开卡数，月底清空）
		 *
		 * @version 1.0.0
		 */
		public static final String LAST_1_MONTH = "sys:last1:month";
		/**
		 * LAST_2_MONTH:TODO（按照省统计上上个月开卡数，月底清空）
		 *
		 * @version 1.0.0
		 */
		public static final String LAST_2_MONTH = "sys:last2:month";
		/**
		 * LAST_3_MONTH:TODO（按照省统计上上上个月开卡数，月底清空）
		 *
		 * @version 1.0.0
		 */
		public static final String LAST_3_MONTH = "sys:last3:month";
		/**
		 * LAST_MONTH_TOP:TODO（全国前三个省份，售卡统计,月底清空）
		 *
		 * @version 1.0.0
		 */
		public static final String SYS_TOP3_MONTH = "sys:top3:month";

	}

	/**
	 * 获取手机验证码存储KEY
	 * 
	 * @param phone
	 * @param msgType
	 * @return
	 */
	public static String getVerifyPhoneKey(String phone, MsgType msgType) {
		return "PHONE:" + phone + ":" + msgType.getName();
	}

	/**
	 * 获取消息存储模板KEY
	 * 
	 * @param msgType
	 * @return
	 */
	public static String getMsgTemplateKey(MsgType msgType) {
		return "MSG:TEMPLATE" + msgType.getName();
	}

}
