package com.jianfei.core.service.thirdpart.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.ModuleType;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.VipCardState;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MessageDto.MsgFlag;
import com.jianfei.core.common.utils.MsgAuxiliary;
import com.jianfei.core.common.utils.ObjectUtils;
import com.jianfei.core.common.utils.SmartLog;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.ServiceMsgBuilder;
import com.jianfei.core.service.base.VipCardManager;
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

	@Autowired
	private MsgInfoManager msgInfoManager;

	@Autowired
	private VipCardManager vipCardManager;

	public MessageDto<Map<String, String>> processMessage(String sourceQ,
			String targerQ) {
		// 从消息队列中去数据
		String result = JedisUtils.rpoplpushQ(sourceQ, targerQ);
		if (StringUtils.isEmpty(result)) { // 结果为空直接返回
			return null;
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
			SmartLog.error(e, ModuleType.MESSAGE_MODULE.getName(),
					StringUtils.EMPTY, "反序列消息体，反序列化消息体异常，消息体为:" + result);
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

		// 手机号
		String userPhone = map.get("userPhone");
		// 接受到消息体，打印日志
		SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
				",获取并反序列化消息体成功，获取消息体:" + JSONObject.toJSONString(map)
						+ ",准备获取并解析短信模板...");

		MessageDto<Map<String, String>> messageDto = new MessageDto<Map<String, String>>();
		String msgType = map.get("msgType");

		// 根据消息类型获取消息模板
		String msgBody = MsgAuxiliary.buildMsgBody(map, msgType);
		if (StringUtils.isEmpty(msgBody)) {
			String message = "获取并解析短信模板失败,消息类型为" + msgType
					+ "的短信解析失败,请审查短息模板解析日志.....";
			SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					message);
			return messageDto.setData(map).setMsgBody(message);
		}

		String vipCardNo = map.get("vipCardNo");
		// 暂时开放001，002，003
		if ("001".equals(msgType) || "002".equals(msgType)
				|| "003".equals(msgType)) {
			SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"放心短信类型为:" + msgType);
		} else {
			SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"暂时禁用短信类型为:" + msgType);
			return null;
		}

		boolean isOk = false;// 操作结果状态
		// 是否是激活vip卡标识
		if (MsgType.ACTIVE_CARD.getName().equals(msgType)) {
			// 激活卡号
			return activeCard(map, msgBody, userPhone);

		} else if (MsgType.BACK_CARD_FINISH.getName().equals(msgType)// 退卡完成后短信
																		// 紧急退卡完成
				|| MsgType.RIGHT_BACK_CARD.getName().equals(msgType)) {

			return backCard(msgBody, userPhone, vipCardNo, map);

		} else {
			SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"准备发送短信，(非激活操作非退卡完成和紧急退卡操作),消息类型为+" + msgType);
			isOk = msgInfoManager.sendMsgInfo(userPhone, msgBody);
		}
		if (!isOk) {
			SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"发送短信，(非激活操作非退卡完成和紧急退卡操作),消息类型为+" + msgType
							+ ",调用短信接口失败，请稍后重试...");
		} else {
			SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"发送短信，(非激活操作非退卡完成和紧急退卡操作),消息类型为+" + msgType + ",发送短信成功...");
		}
		return messageDto;
	}

	/**
	 * 退卡申请后短信 紧急退卡完成
	 * 
	 * @param msgBody
	 * @param userPhone
	 * @param vipCardNo
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	private MessageDto<Map<String, String>> backCard(String msgBody,
			String userPhone, String vipCardNo, Map<String, String> map)
			throws UnrecoverableKeyException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, IOException {

		MessageDto<Map<String, String>> messageDto = new MessageDto<Map<String, String>>();
		messageDto.setData(map);
		String msgType = map.get("msgType");
		SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
				"退卡操作，消息类型为" + msgType + "，准备开始退卡的解绑操作...");
		// 调用空港绑定接口
		if (!airportEasyManager.disabledVipCard(vipCardNo)) {
			SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"退卡操作，解绑失败，调用第三方接口失败，消息类型为" + msgType + "，准备更改卡号为"
							+ vipCardNo + "的卡状态为‘解绑失败’...");
			// 修改卡的状态
			if (vipCardManager.activeAppCard(new MapUtils.Builder()
					.setKeyValue("card_state",
							VipCardState.UNBUNDLING_FAIL.getName())// 更改VIP卡状态
					.setKeyValue("cardNo", vipCardNo).build())) {

				SmartLog.warn(ModuleType.MESSAGE_MODULE.getName(), userPhone,
						"退卡操作，调用空港接口解除绑定卡号为" + vipCardNo
								+ "卡失败，数据库更新卡号状态为‘解绑失败’状态成功");

			} else {
				SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
						"调用空港接口解除绑定卡号为" + vipCardNo
								+ "卡失败，数据库更新卡号状态为‘解绑失败’状态失败");
			}
		} else {
			// 修改卡的状态
			if (vipCardManager
					.activeAppCard(new MapUtils.Builder()
							.setKeyValue("card_state",
									VipCardState.BACK_CARD.getName())// 更改VIP卡状态
							.setKeyValue("cardNo", vipCardNo).build())) {
				SmartLog.info(ModuleType.MESSAGE_MODULE.getClass(), userPhone,
						"退卡操作，调用空港接口解除绑定卡号为" + vipCardNo
								+ "卡成功，数据库更新卡号状态为退卡成功状态成功，准备发送短信...");
			} else {
				SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
						"退卡操作,调用空港接口解除绑定卡号为" + vipCardNo
								+ "卡卡成功，数据库更新卡号状态为退卡成功状态失败，准备发送短信...");
			}
		}

		if (msgInfoManager.sendMsgInfo(userPhone, msgBody)) {
			SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"退卡操作,卡号为" + vipCardNo + "，发送短信成功...");
		} else {
			SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"退卡操作,卡号为" + vipCardNo + "，发送短信失败，请稍后再试...");
		}

		return messageDto;
	}

	/**
	 * 激活卡操作
	 * 
	 * @param map
	 * @param msgBody
	 *            消息体
	 * @param userPhone
	 *            手机号
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	private MessageDto<Map<String, String>> activeCard(Map<String, String> map,
			String msgBody, String userPhone) throws UnrecoverableKeyException,
			KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, IOException {

		MessageDto<Map<String, String>> messageDto = new MessageDto<Map<String, String>>();

		String card = map.get("vipCardNo");
		SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
				"激活卡操作，准备开始对卡号为" + card + "的卡激活操作...");

		AppVipcard vipcard = vipCardManager
				.getVipCardByNo(map.get("vipCardNo"));// 根据卡号从数据库中获取卡的信息

		// 判断卡号是否存在
		if (ObjectUtils.isEmpty(vipcard)) {
			String message = "激活卡操作，从数据库获取卡号为" + card
					+ "的卡信息失败，有可能是数据库操作失败或者卡不存在，请排查定位问题...";
			SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					message);
			return messageDto.setData(map).setMsgBody(message);
		}
		// 卡号
		String cardNo = vipcard.getCardNo();
		SmartLog.info(ModuleType.MESSAGE_MODULE.getName(), userPhone,
				"激活卡操作,从数据库中获取卡号为" + cardNo + "的卡信息成功,开始激活该卡,请稍等....");

		// 激活VIP卡
		if (airportEasyManager.activeVipCard(map.get("vipCardNo"), userPhone,
				map.get("userName"))) {

			SmartLog.info(
					ModuleType.MESSAGE_MODULE.getName(),
					userPhone,
					"激活卡操作,卡号为"
							+ cardNo
							+ "的卡激活成功，恭喜你，准备更新该卡的saleTime字段为当前时间和更新该卡的状态为‘绑定成功未激活状态’");
			// 更新卡的销售时间和该卡的状态为‘绑定成功未激活状态’
			boolean isOk = vipCardManager.activeAppCard(new MapUtils.Builder()
					.setKeyValue("saleTime", new Date())
					.setKeyValue("card_state", VipCardState.ACTIVE.getName())// 更改VIP卡状态为绑定成功未激活
					.setKeyValue("cardNo", cardNo).build());

			if (isOk) {

				SmartLog.info(
						ModuleType.MESSAGE_MODULE.getName(),
						userPhone,
						"激活卡操作，更新卡操作，更新卡号为"
								+ cardNo
								+ "的卡的saleTime字段为当前时间和更新该卡的状态为‘绑定成功未激活状态’成功，恭喜你，准备发送短信，告知客户...");

				if (msgInfoManager.sendMsgInfo(userPhone, msgBody)) {// 激活短信
					SmartLog.info(ModuleType.MESSAGE_MODULE.getName(),
							userPhone, "激活卡操作,激活卡" + cardNo + "成功，发送激活短信成功,谢谢");
				} else {
					SmartLog.error(ModuleType.MESSAGE_MODULE.getName(),
							userPhone, "激活卡操作，激活卡" + cardNo
									+ "成功，但是发送激活短信失败,sorry...");

				}
			} else {
				SmartLog.error(
						ModuleType.MESSAGE_MODULE.getName(),
						userPhone,
						"激活操作，调用空港激活接口激活卡"
								+ cardNo
								+ "成功，但是数据库更新卡的状态为‘绑定成功未激活状态’失败，请确认数据库现在是可用的？谢谢");
			}
			return messageDto;
		}

		SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
				"激活卡操作，调用空港接口,激活卡" + cardNo + "失败，请联系空港,准备更改该卡的状态为激活失败状态...");

		// 更改VIP卡绑定失败
		boolean rs = vipCardManager
				.activeAppCard(new MapUtils.Builder()
						.setKeyValue("card_state",
								VipCardState.ACTIVATE_FAIL.getName())// 更改VIP卡状态为激活未绑定
						.setKeyValue("cardNo", vipcard.getCardNo()).build());

		if (rs) {
			SmartLog.warn(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"激活卡操作，调用空港接口,激活卡" + cardNo + "失败，请联系空港,数据库更改卡为激活失败状态成功...");
		} else {
			SmartLog.error(ModuleType.MESSAGE_MODULE.getName(), userPhone,
					"激活卡操作，调用空港接口,激活卡" + cardNo
							+ "失败，请联系空港,数据库更改卡为激活失败状态失败,请确认数据库是可用的...");
		}
		return messageDto;
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

	private Logger logger = LoggerFactory.getLogger(getClass());
}
