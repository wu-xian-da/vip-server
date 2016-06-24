package com.jianfei.core.service.thirdpart.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.enu.VipCardState;
import com.jianfei.core.dto.ServiceMsgBuilder;
import com.jianfei.core.service.base.VipCardManager;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MessageDto.MsgFlag;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MsgAuxiliary;
import com.jianfei.core.common.utils.ObjectUtils;
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

	public static final String MESSAGEKEY = "SMS_QUEUE_VIP";

	@Autowired
	private AirportEasyManager airportEasyManager;

	@Autowired
	private MsgInfoManager msgInfoManager;

	@Autowired
	private VipCardManager vipCardManager;

	// 没20秒执行一次
	/**
	 * 从消息队列中拉消息
	 */
	@Scheduled(fixedRate = 20000)
	public void pullSmsMessage() {
		MessageDto<Map<String, String>> messageDto = processMessage(MESSAGEKEY,
				QueueManager.SMS_QUEUE_VIP_BAK);
		if (!messageDto.isOk()) {
			LoggerFactory.getLogger(getClass()).error(
					"从" + MESSAGEKEY + "队列拉消息，操作失败。。。,接口反馈信息:"
							+ JSONObject.toJSONString(messageDto));
		}
	}

	public MessageDto<Map<String, String>> processMessage(String sourceQ,
			String targerQ) {
		// 从消息队列中去数据
		String result = JedisUtils.rpoplpushQ(sourceQ, targerQ);
		if (StringUtils.isEmpty(result)) { // 结果为空直接返回
			return new MessageDto<Map<String, String>>().setOk(true)
					.setMsgBody("短信消息队列:从队列中获取消息为空");
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
		return new MessageDto<Map<String, String>>().setMsgBody(MsgFlag.ERROR);
	}

	/**
	 * logicalProcessing(根据队列信息，分业务处理)
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

		MessageDto<Map<String, String>> messageDto = new MessageDto<Map<String, String>>();
		String msgType = map.get("msgType");

		String msgBody = MsgAuxiliary.buildMsgBody(map, msgType);
		if (StringUtils.isEmpty(msgBody)) {
			return messageDto.setData(map).setMsgBody("从缓存中获取指定类型的短信消息模版为空...");
		}

		String userPhone = map.get("userPhone");

		boolean isOk = false;// 操作结果状态
		String message = StringUtils.EMPTY;
		// 是否是激活vip卡标识
		if (MsgType.ACTIVE_CARD.equals(msgType)) {
			AppVipcard vipcard = vipCardManager.getVipCardByNo(map
					.get("vipCardNo"));
			// 判断卡号是否存在
			if (ObjectUtils.isEmpty(vipcard)) {
				LoggerFactory.getLogger(getClass()).error("激活用户帐号失败...");
				return messageDto.setOk(isOk).setData(map)
						.setMsgBody("激活失败，卡号不存在...");
			}
			// 激活VIP卡
			if (airportEasyManager.activeVipCard(map.get("vipCardNo"),
					userPhone, map.get("userName"))) {
				// 计算卡的有效期
				Date expireDate = DateUtil.addDays(new Date(),
						vipcard.getValideTime());
				// 更新激活时间和卡的有效期
				if (vipCardManager.activeAppCard(new MapUtils.Builder()
						.setKeyValue("expiryTime", expireDate)
						.setKeyValue("card_state",
								VipCardState.ACTIVE.getName())// 更改VIP卡状态
						.setKeyValue("cardNo", vipcard.getCardNo()).build())) {
					isOk = msgInfoManager.sendMsgInfo(userPhone, msgBody);// 激活短信
				}
			} else {
				LoggerFactory.getLogger(getClass()).error("激活用户帐号失败...");
				message = "激活用户帐号失败...";
				// 更改VIP卡状态为未激活
				vipCardManager.activeAppCard(new MapUtils.Builder()
						.setKeyValue("card_state",
								VipCardState.ACTIVATE_FAIL.getName())// 更改VIP卡状态
						.setKeyValue("cardNo", vipcard.getCardNo()).build());
			}
		} else {
			// 登入，注册，退卡，退卡完成短信
			isOk = msgInfoManager.sendMsgInfo(userPhone, msgBody);
		}
		if (!isOk && StringUtils.isEmpty(message)) {
			message = "调用短信接口失败...";
		}
		return messageDto.setOk(isOk).setData(map).setMsgBody(message);
	}

	public static void main(String[] args) {

	}

	/**
	 * 发送消息
	 *
	 * @param msgBuilder
	 * @return
	 */
	@Override
	public boolean sendMessage(ServiceMsgBuilder msgBuilder) {
		String fastJsonStr = JSON.toJSONString(msgBuilder);
		return JedisUtils.lpushString(MESSAGEKEY, fastJsonStr) == null ? false
				: true;
	}
}
