/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:15:27
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.cache.CacheCons.Sys;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.AirportEasyUseInfo;
import com.jianfei.core.mapper.ArchiveMapper;
import com.jianfei.core.service.order.ConsumeManager;
import com.jianfei.core.service.stat.ArchiveManager;
import com.jianfei.core.service.thirdpart.AirportEasyManager;

/**
 *
 * @Description: 订单归档，后台根据角色不同，首页展示不同的图表
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午5:15:27
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class ArchiveManagerImpl implements ArchiveManager {

	@Autowired
	private ArchiveMapper archiveMapper;
	@Autowired
	private AirportEasyManager airportEasyManager;
	@Autowired
	ConsumeManager consumeManager;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#masterTotal(java.util.Map)
	 */
	@Override
	public Map<String, Object> masterTotal(Map<String, Object> map) {
		return archiveMapper.masterTotal(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#masterTop(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> masterTop(Map<String, Object> map) {
		List<Map<String, Object>> list = null;
		Object object = JedisUtils.getObject(CacheCons.Sys.LAST_MONTH_TOP3);
		if (null != object) {
			list = (List<Map<String, Object>>) JedisUtils
					.getObject(CacheCons.Sys.LAST_MONTH_TOP3);
		} else {
			list = archiveMapper.masterTop(map);
			JedisUtils.setObject(CacheCons.Sys.LAST_MONTH_TOP3, list, 0);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#masterDraw(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> masterDraw(Map<String, Object> map,
			String cacheKey) {
		List<Map<String, Object>> maps = null;
		Object object = JedisUtils.getObject(cacheKey);
		if (null != object) {
			maps = (List<Map<String, Object>>) JedisUtils.getObject(cacheKey);
		} else {
			maps = archiveMapper.masterDraw(map);
			JedisUtils.setObject(cacheKey, maps, 0);
		}
		return maps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#zhuGuanTotal(java.util.Map)
	 */
	@Override
	public Map<String, Object> zhuGuanTotal(Map<String, Object> map,
			String cacheKey) {

		// Map<String, Object> rsMap = null;
		// Object object = JedisUtils
		// .getObject(CacheCons.Sys.LAST_MONTH_TOTAL_ZHUGUAN);
		// if (null != object) {
		// rsMap = (Map<String, Object>) JedisUtils
		// .getObject(CacheCons.Sys.LAST_MONTH_TOTAL_ZHUGUAN);
		// } else {
		// rsMap = archiveMapper.zhuGuanTotal(map);
		// JedisUtils.setObject(CacheCons.Sys.LAST_MONTH_TOTAL_ZHUGUAN, rsMap,
		// 0);
		// }
		return archiveMapper.zhuGuanTotal(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#zhuGuanAllAirPort(java.util
	 * .Map)
	 */
	@Override
	public List<Map<String, Object>> zhuGuanAllAirPort(Map<String, Object> map,
			String cacheKey) {
		// TODO
		return archiveMapper.zhuGuanAllAirPort(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#zhuGuanDraw(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> zhuGuanDraw(Map<String, Object> map,
			String cacheKey) {
		// TODO
		return archiveMapper.zhuGuanDraw(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#baseDailyExtract(java.util
	 * .Map)
	 */
	@Override
	public void baseDailyExtract(Map<String, Object> map) {
		archiveMapper.baseDailyExtract(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.stat.ArchiveManager#selectOrderMaxDay()
	 */
	@Override
	public Map<String, Object> selectOrderMaxDay() {
		return archiveMapper.selectOrderMaxDay();
	}

	/**
	 * dailyOrderArchice(每天23点58分执行定时任务) void
	 * 
	 * @version 1.0.0
	 */
	@Scheduled(cron = "0 58 23 * * ? ")
	public void dailyOrderArchice() {
		logger.info("开始执行订单归档任务...");
		baseDailyExtract(selectOrderMaxDay());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#masterHome(org.springframework
	 * .ui.Model)
	 */
	@Override
	public void masterHome(Model model) {
		Map<String, Object> map = masterTotal(new MapUtils.Builder().build());
		model.addAttribute("total", map.get("total"));
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		model.addAttribute("dataStr", lastMoth.get("dataStr"));
		model.addAttribute("top", masterTop(lastMoth));
		model.addAttribute(
				"draw1",
				handDraw(masterDraw(lastMoth, CacheCons.Sys.LAST_1_MONTH),
						"省份/月份开卡数", lastMoth.get("dataStr")));

		model.addAttribute(
				"draw2",
				handDraw(
						masterDraw(DateUtil.getDelayDate(2), Sys.LAST_2_MONTH),
						"省份/月份开卡数", DateUtil.getDelayDate(2).get("dataStr")));

		model.addAttribute(
				"draw3",
				handDraw(
						masterDraw(DateUtil.getDelayDate(3),
								CacheCons.Sys.LAST_3_MONTH), "省份/月份开卡数",
						DateUtil.getDelayDate(3).get("dataStr")));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#chargeHome(org.springframework
	 * .ui.Model, com.jianfei.core.bean.User)
	 */
	@Override
	public void chargeHome(Model model, User user) {
		List<AriPort> ariPorts = user.getAripors();
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		List<String> list = new ArrayList<String>();
		for (AriPort ariPort : ariPorts) {
			list.add(ariPort.getId());
		}
		lastMoth.put("ariportIds", list);
		// 管辖区域范围内总的订单数
		Map<String, Object> map = zhuGuanTotal(lastMoth,
				CacheCons.Sys.LAST_MONTH_TOTAL_ZHUGUAN);
		map = map == null ? new MapUtils.Builder().setKeyValue("total", 0)
				.build() : map;
		model.addAttribute("dataStr", lastMoth.get("dataStr"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute(
				"airPorts",
				zhuGuanAllAirPort(lastMoth,
						CacheCons.Sys.LAST_MONTH_All_ZHUGUAN));
		List<Map<String, Object>> mapList = zhuGuanDraw(lastMoth, "");
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> m : mapList) {
			Map<String, Object> mb = new HashMap<String, Object>();
			String xAxis = m.get("bnames") == "" ? "" : m.get("bnames")
					.toString();
			mb.put("text", m.get("airportname") + ":横轴业务员,纵轴上月开卡数");
			mb.put("xAxis", xAxis.split(","));
			mb.put("series", StringUtils.stringArray2Long(
					m.get("order_nums") == "" ? "" : m.get("order_nums")
							.toString(), ","));
			maps.add(mb);
		}
		model.addAttribute("draw", JSONObject.toJSONString(maps));
	}

	public Map<String, String> handDraw(List<Map<String, Object>> draw,
			String text, Object title) {
		Map<Object, Object> drawMap = new HashMap<Object, Object>();
		for (Map<String, Object> m1 : draw) {
			drawMap.put(m1.get("province"), m1.get("order_num"));
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("xAxis", StringUtils.join(drawMap.keySet(), ","));
		map.put("series", StringUtils.join(drawMap.values(), ","));
		map.put("text", text);
		map.put("title", title == null ? "开卡数" : title.toString() + "开卡数");
		return map;
	}

	
	/**
	 * 定时获取空港的核销数据
	 * 每小时获取一次
	 */
	@Scheduled(cron = "0 0 * * * *")
	public void checkinDataSchedule() {
		logger.info("<<<<<<获取空港核销数据>>>>>>");
		try {
			AirportEasyUseInfo aeInfo = airportEasyManager.getVipCardUseInfo();
			if (aeInfo != null){
				airportEasyManager.sendConfirmInfo(aeInfo.getBatchNo());
				List<AppConsume> clist = aeInfo.getConsumeList();
				if (clist != null){
					int size = aeInfo.getConsumeList().size();
					for (int i=0;i<size;i++){
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#dateProvinceIdRedisCache
	 * (java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> dateProvinceIdRedisCache(
			Map<String, Object> map) {
		return archiveMapper.dateProvinceIdApportIds(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#dateProvinceIdApportIds(
	 * java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> dateProvinceIdApportIds(
			Map<String, Object> map) {
		return archiveMapper.dateProvinceIdApportIds(map);
	}
}
