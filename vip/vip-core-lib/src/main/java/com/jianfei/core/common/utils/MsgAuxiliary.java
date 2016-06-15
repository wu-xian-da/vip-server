/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月15日-上午11:45:04
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.MsgType;

/**
 *
 * @Description: 队列消息辅助类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月15日 上午11:45:04
 * 
 * @version 1.0.0
 *
 */
public class MsgAuxiliary {
	// log日志
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * validateMSgType(验证是否是合法的消息类型)
	 * 
	 * @param msgType
	 *            消息类型
	 * @return boolean
	 * @version 1.0.0
	 */
	public static boolean validateMSgType(String msgType) {
		for (MsgType mType : MsgType.values()) {
			if (mType.equals(msgType)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
	}

	/**
	 * buildMsgBody(解析模版构建短信消息内容)
	 * 
	 * @param map
	 *            消息队列内容
	 * @param msgType
	 *            消息类型
	 * @return String
	 * @version 1.0.0
	 */
	public static String buildMsgBody(Map<String, String> map, String msgType) {
		String cacheKey = StringUtils.EMPTY;
		for (MsgType mType : MsgType.values()) {
			if (mType.getName().equals(msgType)) {
				cacheKey = CacheCons.getMsgTemplateKey(mType);
			}
		}
		if (StringUtils.isEmpty(cacheKey)) {
			LoggerFactory.getLogger(MsgAuxiliary.class).error("短信信息类型错误...");
			return StringUtils.EMPTY;
		}
		String msgBody = JedisUtils.get(cacheKey);

		if (StringUtils.isEmpty(msgBody)) {
			LoggerFactory.getLogger(MsgAuxiliary.class).error(
					"从缓存中获取短信消息模版为空...");
			return StringUtils.EMPTY;
		}
		// 解析模版
		msgBody = msgBody.replaceAll("\\[userPhone\\]", map.get("userPhone"))
				.replaceAll("\\[vipCardNo\\]", map.get("vipCardNo"));

		if (!StringUtils.isEmpty(map.get("userName"))) {
			msgBody = msgBody.replaceAll("\\[userName\\]", map.get("userName"));
		}

		if (!StringUtils.isEmpty(map.get("msgBody"))) {
			// 激活帐号信息
			String msgBodyRs = map.get("msgBody");
			@SuppressWarnings("unchecked")
			Map<String, String> msgMap = JSONObject.parseObject(msgBodyRs,
					Map.class);

			msgBody = msgBody.replaceAll("\\[code\\]", msgMap.get("code"))
					.replaceAll("\\[time\\]", msgMap.get("time"));
		}

		return msgBody;
	}
}
