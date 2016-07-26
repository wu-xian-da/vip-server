package com.jianfei.scheduled.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.VipCardState;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.ObjectUtils;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.AirportEasyUseInfo;
import com.jianfei.core.service.base.VipCardManager;
import com.jianfei.core.service.order.ConsumeManager;
import com.jianfei.core.service.stat.ArchiveManager;
import com.jianfei.core.service.thirdpart.AirportEasyManager;
import com.jianfei.core.service.thirdpart.QueueManager;
import com.jianfei.scheduled.ITaskManager;

/**
 *
 * @Description: 管理定时任务
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月28日 下午2:35:25
 * 
 * @version 1.0.0
 *
 */
@Service
public class TaskManagerImpl implements ITaskManager {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ArchiveManager archiveManager;

	@Autowired
	private AirportEasyManager airportEasyManager;

	@Autowired
	private ConsumeManager consumeManager;
	@Autowired
	private QueueManager queueManager;
	@Autowired
	private VipCardManager vipCardManager;

	/**
	 * dailyOrderArchice(每天23点58分执行定时任务) void
	 * 
	 * @version 1.0.0
	 */
	@Scheduled(cron = "0 58 23 * * ? ")
	public void dailyOrderArchice() {
		logger.info("<<<<<<开始执行订单归档任务>>>>>>");
		System.out.println("<<<<<<开始执行订单归档任务了>>>>>>");
		archiveManager.baseDailyExtract(DateUtil.dailyExtractDate());
		archiveManager.dateProvinceIdCache(DateUtil.getCurrentTime());
		archiveManager.dateProvinceIdApportIds(DateUtil.getCurrentTime());
	}

	/*每10分钟执行一次，启动后推迟10分钟执行
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.scheduled.ITaskManager#checkinDataSchedule()
	 */
	//@Scheduled(cron = "0 0 * * * *")
	//@Scheduled(cron = "0 */10 * * * ?")
	@Scheduled(fixedRate = 600000,initialDelay=600000)
	public void checkinDataSchedule() {
		logger.info("<<<<<<获取空港核销数据>>>>>>");
		try {
			AirportEasyUseInfo aeInfo = airportEasyManager.getVipCardUseInfo();
			if (aeInfo != null) {
				airportEasyManager.sendConfirmInfo(aeInfo.getBatchNo());
				List<AppConsume> clist = aeInfo.getConsumeList();
				if (clist != null) {
					int size = aeInfo.getConsumeList().size();
					for (int i = 0; i < size; i++) {
						AppConsume appConsume = clist.get(i);
						consumeManager.addConsume(appConsume);

						// 判断是否是第一次使用，如果是，redis计时加1，更新卡的第一次使用时间和有效时间，如果不是,redis计时累计加1
						try {
							firstServiceHandle(appConsume);
						} catch (Exception e) {
							logger.error("对卡信息使用次数统计异常:" + e.getMessage());
						}
						return;
					}
				}

			}

		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 每1秒执行一次
	/**
	 * 从消息队列中拉消息
	 */
	@Scheduled(fixedRate = 1000)
	public void pullSmsMessage() {
		MessageDto<Map<String, String>> messageDto = queueManager
				.processMessage(QueueManager.MESSAGEKEY,
						QueueManager.SMS_QUEUE_VIP_BAK);
		if (null != messageDto) {
			if (messageDto.isOk()) {
				System.out.println(DateUtil.dateToString(new Date(),
						DateUtil.ALL_FOMAT)
						+ "->jianfei-info->"
						+ JSONObject.toJSONString(messageDto));
				LoggerFactory.getLogger(getClass()).info(
						JSONObject.toJSONString(messageDto));
			} else {
				System.out.println(DateUtil.dateToString(new Date(),
						DateUtil.ALL_FOMAT)
						+ "->jianfei-error->"
						+ JSONObject.toJSONString(messageDto));
				LoggerFactory.getLogger(getClass()).error(
						JSONObject.toJSONString(messageDto));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.scheduled.ITaskManager#validateOrdereIsEfective()
	 */
	@Scheduled(fixedRate = 1800000, initialDelay = 1800000)
	@Override
	public void validateOrdereIsEfective() {
		logger.info(DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)
				+ "<<<<<<开始扫描无效订单>>>>>>>");
		Date d = DateUtil.addInteger(new Date(), Calendar.MINUTE, -30);
		String endTime = DateUtil.dateToString(d, DateUtil.ALL_FOMAT);
		int result = archiveManager.validateOrdereIsEfectiveAndHadnle(endTime);
		logger.info("<<<<<<每隔30分钟扫描订单是否有效:发现" + result + "条无效订单。>>>>>>>");
	}

	/**
	 * 判断卡是否的hi第一次消费<br>
	 * A.如果是<br>
	 * 1.更改卡的第一次消费时间<br>
	 * 2。根据卡的有效期计算卡的到期时间<br>
	 * 3.redis内存计数器对该卡的消费次数统计<br>
	 * B.如果不是<br>
	 * 3.redis内存计数器对该卡的消费次数统计<br>
	 * 
	 * @param appConsume
	 *            卡的信息
	 * @return true:是第一次消费，false:不是第一次消费
	 */
	private boolean firstServiceHandle(AppConsume appConsume) {
		if (ObjectUtils.isEmpty(appConsume)
				|| ObjectUtils.isEmpty(appConsume.getConsumeTime())
				|| StringUtils.isEmpty(appConsume.getCardNo())) {
			throw new IllegalArgumentException("参数不为空为空|消费时间不能为空|卡号不能为空...");
		}
		// 根据卡号从数据库中获取卡的信息
		AppVipcard vipcard = vipCardManager.getVipCardByNo(appConsume
				.getCardNo());
		if (ObjectUtils.isEmpty(vipcard)) {
			throw new IllegalArgumentException("数据查询不到卡号为"
					+ appConsume.getCardNo() + "的信息，无法获取卡的有效期，请排查");
		}
		// 计算卡的有效时间
		Date expireDate = DateUtil.addDays(appConsume.getConsumeTime(),
				vipcard.getValideTime());
		// 消费次数计算
		Long result = JedisUtils.incr(appConsume.getCardNo());
		if (1 == result) {// 第一次消费
			// 更新卡的第一次消费时间和卡有效期到期时间
			if (vipCardManager.activeAppCard(new MapUtils.Builder()
					.setKeyValue("activeTime", appConsume.getConsumeTime())
					// 更改激活时间为第一次消费时间
					.setKeyValue("cardNo", appConsume.getCardNo())
					.setKeyValue("card_state",
							VipCardState.ACTIVE_USE.getName())// 更改VIP卡状态为
																// 绑定成功已激活
					.setKeyValue("expiryTime", expireDate).build())) {// 更改卡的有效期
				String infoMsg = "卡号为"
						+ appConsume.getCardNo()
						+ "第一次消费时间为:"
						+ DateUtil.dateToString(appConsume.getConsumeTime(),
								DateUtil.ALL_FOMAT);
				logger.info(infoMsg);
				System.out.println(infoMsg);
			} else {
				String erroMsg = "卡号为" + appConsume.getCardNo()
						+ "第一次消费信息保存到数据库失败。。。";
				logger.error(erroMsg);
				System.out.println(erroMsg);
			}
			return true;
		}
		return false;
	}

	/**
	 * 每天0点0分0秒检查一次 检查vip卡的是否已过期 过期将vip卡的状态改为 VipCardState.CARD_EXPIRED
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void checkExpiredOfCard() {
		// TODO Auto-generated method stub
		logger.info(DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)
				+ "<<<<<<检查是否有过期的vip卡>>>>>>>");
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("checkFlag", "check");
		List<AppVipcard> vipCardList = vipCardManager
				.showCardListNotPage(reqMap);
		if (vipCardList != null && vipCardList.size() > 0) {
			for (AppVipcard vipCard : vipCardList) {
				if (vipCard.getExpiryTime().before(new Date())) {// 过期
					vipCard.setCardState(VipCardState.CARD_EXPIRED.getName());
					vipCardManager.updateByPrimaryKeySelective(vipCard);
				}
			}
		}

	}
}
