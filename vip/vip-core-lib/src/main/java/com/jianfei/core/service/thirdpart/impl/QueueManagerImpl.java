package com.jianfei.core.service.thirdpart.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MsgAuxiliary;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.thirdpart.AirportEasyManager;
import com.jianfei.core.service.thirdpart.MsgInfoManager;
import com.jianfei.core.service.thirdpart.QueueManager;

/**
 *
 * @Description: 队列消息管理实现类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月14日 下午5:52:22
 *
 * @version 1.0.0
 *
 */
@Service
public class QueueManagerImpl implements QueueManager {

	@Autowired
	private AirportEasyManager airportEasyManager;

	/**
	 * msgInfoManager:TODO（短信消息处理接口）
	 *
	 * @version 1.0.0
	 */
	@Autowired
	private MsgInfoManager msgInfoManager;

	public MessageDto<Map<String, String>> processMessage(String sourceQ,
			String targerQ) {
		// 从消息队列中去数据
		// String result = JedisUtils.rpoplpushQ(sourceQ, targerQ);
		String result = "";
		if (StringUtils.isEmpty(result)) { // 结果为空直接返回
			LoggerFactory.getLogger(getClass()).error("短信消息队列:从队列中获取消息为空");
			return new MessageDto<Map<String, String>>();
		}

		try {
			// 反序列化结果
			@SuppressWarnings({ "unchecked" })
			Map<String, String> returnMap = JSONObject.parseObject(result,
					Map.class);
			// 处理业务
			return logicalProcessing(returnMap);
		} catch (UnrecoverableKeyException | KeyManagementException
				| NoSuchAlgorithmException | KeyStoreException | IOException e) {
			LoggerFactory.getLogger(getClass()).error("处理短信消息队列:{}",
					e.getMessage());
		}
		return new MessageDto<Map<String, String>>();
	}

	/**
	 * logicalProcessing(这里用一句话描述这个方法的作用)
	 *
	 * @param map
	 * @return boolean
	 * @version 1.0.0
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 */
	private MessageDto<Map<String, String>> logicalProcessing(
			Map<String, String> map) throws UnrecoverableKeyException,
			KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, IOException {
		String msgType = map.get("msgType");
		String msgBody = MsgAuxiliary.buildMsgBody(map, msgType);

		String userPhone = map.get("userPhone");

		boolean isOk = false;// 操作结果状态
		// 是否是激活vip卡标识
		if (MsgType.ACTIVE_CARD.equals(msgType)
				&& !StringUtils.isEmpty(msgBody)) {
			// 激活VIP卡
			if (airportEasyManager.activeVipCard(map.get("vipCardNo"),
					userPhone, map.get("userName"))) {
				isOk = msgInfoManager.sendMsgInfo(userPhone, msgBody);// 激活短信
			} else {
				LoggerFactory.getLogger(getClass()).error("激活用户帐号失败...");
			}
		} else {
			// 登入，注册，退卡，退卡完成短信
			isOk = msgInfoManager.sendMsgInfo(userPhone, msgBody);
		}
		return new MessageDto<Map<String, String>>().setOk(isOk).setData(map);
	}
}
