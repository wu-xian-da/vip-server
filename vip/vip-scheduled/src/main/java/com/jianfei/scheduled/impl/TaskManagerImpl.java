package com.jianfei.scheduled.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.dto.AirportEasyUseInfo;
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
		archiveManager.dateProvinceIdRedisCache(DateUtil.getCurrentTime());
		archiveManager.dateProvinceIdApportIds(DateUtil.getCurrentTime());
	}

	/**
	 * 定时获取空港的核销数据 每小时获取一次
	 */
	@Scheduled(cron = "0 0 * * * *")
	//@Scheduled(cron = "0 */1 * * * ?")
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
	@Scheduled(fixedRate = 1800000)
	@Override
	public void validateOrdereIsEfective() {
		logger.info(DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)
				+ "<<<<<<开始扫描无效订单>>>>>>>");
		Date d = DateUtil.addInteger(new Date(), Calendar.MINUTE, -30);
		String endTime = DateUtil.dateToString(d, DateUtil.ALL_FOMAT);
		int result = archiveManager.validateOrdereIsEfectiveAndHadnle(endTime);
		logger.info("<<<<<<每隔30分钟扫描订单是否有效:发现" + result + "条无效订单。>>>>>>>");
	}

}
