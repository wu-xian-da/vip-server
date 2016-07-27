/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月15日-上午11:45:04
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.ModuleType;
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
		// 获取模板类型
		String cacheKey = StringUtils.EMPTY;
		for (MsgType mType : MsgType.values()) {
			if (mType.getName().equals(msgType)) {
				cacheKey = CacheCons.getMsgTemplateKey(mType);
			}
		}
		// 手机号
		String userPhone = map.get("userPhone");
		// 判断消息类型是否有效
		if (StringUtils.isEmpty(cacheKey)) {
			SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"判断消息类型：很抱歉，该消息类型是无效的消息类型...");
			return StringUtils.EMPTY;
		}
		// 从缓存中获取短信模板
		String msgBody = JedisUtils.get(cacheKey);

		// 验证短信模板是否有效
		if (StringUtils.isEmpty(msgBody)) {
			SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"该消息类型有效，但是消息类型为" + cacheKey
							+ ",从缓存中获取短信您模板为空，请确认redis是否宕机还是没有改消息模板...");
			return StringUtils.EMPTY;
		}

		SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
				"获取消息模板成功，根据消息类型" + cacheKey + ",从缓存中获取短信模板成功,准备开始解析短信模板....");
		// 解析模版
		msgBody = msgBody.replaceAll("\\[userPhone\\]",
				returnValue(map, "userPhone")).replaceAll("\\[vipCardNo\\]",
				returnValue(map, "vipCardNo"));

		if (!StringUtils.isEmpty(returnValue(map, "userName"))) {
			msgBody = msgBody.replaceAll("\\[userName\\]", map.get("userName"));
		}

		if (!StringUtils.isEmpty(map.get("msgBody"))) {
			String msgBodyRs = map.get("msgBody");
			@SuppressWarnings("unchecked")
			Map<String, Object> msgMap = JSONObject.parseObject(msgBodyRs,
					Map.class);
			Set<String> setKeys = msgMap.keySet();
			for (String key : setKeys) {
				msgBody = msgBody.replaceAll("\\[" + key + "\\]",
						StringUtils.obj2String(msgMap.get(key)));
			}
		}

		SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
				"模板解析成功，消息类型" + cacheKey + "的短信模板解析成功，恭喜你，,待发送短信内容为：" + msgBody);

		return msgBody;
	}

	public static String returnValue(Map<String, String> map, String key) {
		if (null != map.get(key)) {
			return map.get(key);
		}
		return StringUtils.EMPTY;
	}
}
