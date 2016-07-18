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
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.VipCardState;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MessageDto.MsgFlag;
import com.jianfei.core.common.utils.MsgAuxiliary;
import com.jianfei.core.common.utils.ObjectUtils;
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
		System.out.println(DateUtil
				.dateToString(new Date(), DateUtil.ALL_FOMAT)
				+ "->messageBody->队列消息体->:" + result);

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
		System.out.println("vipjianfei:template:"
				+ DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)
				+ "->templateAnalysis->" + msgBody);
		if (StringUtils.isEmpty(msgBody)) {
			return messageDto.setData(map).setMsgBody("从缓存中获取指定类型的短信消息模版为空...");
		}

		map.put("templateAnalysisBody", msgBody);

		String userPhone = map.get("userPhone");
		String vipCardNo = map.get("vipCardNo");
		boolean isOk = false;// 操作结果状态
		// 是否是激活vip卡标识
		if (MsgType.ACTIVE_CARD.getName().equals(msgType)) {

			return activeCard(map, msgBody, userPhone);

		} else if (MsgType.BACK_CARD_FINISH.getName().equals(msgType)// 退卡完成后短信
																		// 紧急退卡完成
				|| MsgType.RIGHT_BACK_CARD.getName().equals(msgType)) {

			return backCard(msgBody, userPhone, vipCardNo, map);

		} else {
			// 登入，注册，退卡申请短信
			System.out.println("jianfei->"
					+ DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)
					+ "->登入|注册|退卡申请|取消退卡短信|其他支付 退卡申请后短信。。。->" + msgBody + "  手机号：" + userPhone);
			isOk = msgInfoManager.sendMsgInfo(userPhone, msgBody);
		}
		if (!isOk) {
			messageDto.setOk(isOk).setMsgBody("调用短信接口失败...");
		} else {
			messageDto.setMsgBody("发送短信成功...").setOk(isOk);
		}
		return messageDto.setData(map);
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

		// 调用空港绑定接口
		if (!airportEasyManager.disabledVipCard(vipCardNo)){
			System.out.println("vipjianfei:解绑失败:"
					+ DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)
					+ "->退卡申请后短信, 紧急退卡完成短信>" + msgBody + "  手机号:" + userPhone
					+ "  卡号：" + vipCardNo);
			// 修改卡的状态
			if (vipCardManager.activeAppCard(new MapUtils.Builder()
					.setKeyValue("card_state",
							VipCardState.UNBUNDLING_FAIL.getName())// 更改VIP卡状态
					.setKeyValue("cardNo", vipCardNo).build())) {
				messageDto.setMsgBody("调用空港接口解除绑定卡号为" + vipCardNo
						+ "卡失败，数据库更新卡号状态为解除绑定状态成功");
			} else {
				messageDto.setMsgBody("调用空港接口解除绑定卡号为" + vipCardNo
						+ "卡失败，数据库更新卡号状态为解除绑定状态失败");
			}
		}
		
		System.out.println("vipjianfei:vipbacksuccess:"
				+ DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)
				+ "->退卡完成短信, 紧急退卡完成短信>" + msgBody + "  手机号:" + userPhone
				+ "  卡号：" + vipCardNo);
		if (msgInfoManager.sendMsgInfo(userPhone, msgBody)) {
			messageDto.setOk(true)
					.setMsgBody(
							"调用空港接口，杰出卡号为" + vipCardNo + "卡成功，发送短信到"
									+ userPhone + "成功");
		} else {
			messageDto.setMsgBody("调用空港接口，杰出卡号为" + vipCardNo + "卡成功，发送短信到"
					+ userPhone + "失败");
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
		messageDto.setData(map);
		AppVipcard vipcard = vipCardManager
				.getVipCardByNo(map.get("vipCardNo"));// 根据卡号从数据库中获取卡的信息

		// 判断卡号是否存在
		if (ObjectUtils.isEmpty(vipcard)) {
			logger.error("vipjinfei:vip激活:卡号为" + map.get("vipCardNo")
					+ "的卡不存在...");
			return messageDto.setData(map).setMsgBody(
					"vipjianfei:vip激活:卡号为" + map.get("vipCardNo") + "的卡不存在...");
		}
		String cardNo = vipcard.getCardNo();

		// 激活VIP卡
		if (airportEasyManager.activeVipCard(map.get("vipCardNo"), userPhone,
				map.get("userName"))) {
			System.out.println("vipjianfei:vipactivesuccess:"
					+ DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)
					+ "->激活成功。。。>" + msgBody + "  手机号:" + userPhone + "  卡号："
					+ cardNo);
			// 计算卡的有效期
//			Date expireDate = DateUtil.addDays(new Date(),
//					vipcard.getValideTime());
			// 更新激活时间和卡的有效期
			boolean isOk = vipCardManager.activeAppCard(new MapUtils.Builder()
					.setKeyValue("activeTime", new Date())
//					.setKeyValue("expiryTime", expireDate)
					.setKeyValue("card_state", VipCardState.ACTIVE.getName())// 更改VIP卡状态
					.setKeyValue("cardNo", cardNo).build());

			if (isOk) {
				System.out.println("vipjinfei:"
						+ DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)
						+ "->激活短信->" + msgBody + "  手机号：" + userPhone + "  卡号："
						+ cardNo);
				if (msgInfoManager.sendMsgInfo(userPhone, msgBody)) {// 激活短信
					messageDto.setOk(true).setMsgBody(
							"调用空港接口激活卡号为" + cardNo + "卡成功,发送短信成功。。。");
				} else {
					messageDto.setMsgBody("调用空港接口激活卡号为" + cardNo
							+ "卡成功,发送短信成功。。。");
				}
			} else {
				messageDto.setOk(true).setMsgBody(
						"调用空港接口激活卡号为" + cardNo + "卡成功,数据库更新卡的状态为激活状态失败。。");
			}
			return messageDto;
		}

		// 更改VIP卡绑定失败
		boolean rs = vipCardManager
				.activeAppCard(new MapUtils.Builder()
						.setKeyValue("card_state",
								VipCardState.ACTIVATE_FAIL.getName())// 更改VIP卡状态
						.setKeyValue("cardNo", vipcard.getCardNo()).build());
		if (rs) {
			messageDto.setMsgBody("调用空港接口激活卡号为" + vipcard.getCardNo()
					+ "卡失败,数据库更改卡为激活失败状态成功...");
		} else {
			messageDto.setMsgBody("调用空港接口激活卡号为" + vipcard.getCardNo()
					+ "卡失败,并且数据库更改卡为激活失败状态失败...");
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
