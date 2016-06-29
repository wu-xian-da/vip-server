/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:15:27
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.CacheCons.Sys;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.mapper.ArchiveMapper;
import com.jianfei.core.service.stat.ArchiveManager;

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
		Object object = JedisUtils.getObject(CacheCons.Sys.SYS_TOP3_MONTH);
		if (null != object) {
			list = (List<Map<String, Object>>) JedisUtils
					.getObject(CacheCons.Sys.SYS_TOP3_MONTH);
		} else {
			list = archiveMapper.masterTop(map);
			JedisUtils.setObject(CacheCons.Sys.SYS_TOP3_MONTH, list, 0);
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
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#masterHome(org.springframework
	 * .ui.Model)
	 */
	@Override
	public void masterHome(Model model, User user) {
		// 获取所有的权限区域 经理
		List<AriPort> ariPorts = user.getAripors();
		List<String> list = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		for (AriPort ariPort : ariPorts) {
			list.add(ariPort.getId());
			buffer.append("'"+ariPort.getId()+"',");
		}
		System.out.println(buffer);
		if (CollectionUtils.isEmpty(list)) {
			model.addAttribute("error", "true");
			return;
		}
		// 查询管辖区域内总的开卡数
		Map<String, Object> map = masterTotal(new MapUtils.Builder()
				.setKeyValue("ariportIds", list).build());
		if (MapUtils.isEmpty(map)) {
			model.addAttribute("total", 0);
		} else {
			model.addAttribute("total", map.get("total"));
		}

		// 构造查询条件
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		lastMoth.put("ariportIds", list);
		// 查询的时间条件，前端展示
		model.addAttribute("dataStr", lastMoth.get("dataStr"));

		// 开卡数前top3的省份
		model.addAttribute("top", masterTop(lastMoth));
		// 柱状图报表数据
		model.addAttribute(
				"draw1",
				handDraw(masterDraw(lastMoth, CacheCons.Sys.LAST_1_MONTH),
						"省份/月份开卡数", lastMoth.get("dataStr")));// 上个月各个省份的开卡数

		model.addAttribute(
				"draw2",
				handDraw(
						masterDraw(DateUtil.getDelayDate(2), Sys.LAST_2_MONTH),
						"省份/月份开卡数", DateUtil.getDelayDate(2).get("dataStr")));// 上上个月各个省份开卡数

		model.addAttribute(
				"draw3",
				handDraw(
						masterDraw(DateUtil.getDelayDate(3),
								CacheCons.Sys.LAST_3_MONTH), "省份/月份开卡数",
						DateUtil.getDelayDate(3).get("dataStr")));// 上上上个月各个省份的开卡数

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
		if (CollectionUtils.isEmpty(list)) {
			model.addAttribute("error", "true");
			return;
		}
		lastMoth.put("ariportIds", list);
		// 管辖区域范围内总的订单数
		Map<String, Object> map = zhuGuanTotal(lastMoth,
				CacheCons.Sys.SYS_HISTORY_ORDERS_ZHUGUAN);

		model.addAttribute("dataStr", lastMoth.get("dataStr"));
		if (MapUtils.isEmpty(map)) {
			model.addAttribute("total", 0);
		} else {
			model.addAttribute("total", map.get("total"));
		}
		// 上个月管辖区域内的开卡数
		model.addAttribute(
				"airPorts",
				zhuGuanAllAirPort(lastMoth,
						CacheCons.Sys.SYS_LASTMONTH_ORDERS_ZHUGUAN));
		// 管辖区域内每个场站每个业务员订单统计
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

		List<Map<String, Object>> maps = archiveMapper
				.dateProvinceIdRedisCache(map);
		if (CollectionUtils.isEmpty(maps)) {
			logger.error(map.get("currentTime").toString() + ":按照省的归档数据为空。。。");
			return maps;
		}
		for (Map<String, Object> m : maps) {
			JedisUtils.setObject(m.get("cacheKey").toString(),
					JSONObject.toJSONString(m), 0);
			logger.info("按照省归档key:" + m.get("cacheKey").toString() + "  value:"
					+ JSONObject.toJSONString(m));
		}
		return maps;
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
		List<Map<String, Object>> maps = archiveMapper
				.dateProvinceIdApportIds(map);

		if (CollectionUtils.isEmpty(maps)) {
			logger.error(map.get("currentTime").toString() + ":按照省和机场归档数据为空。。。");
			return maps;
		}
		for (Map<String, Object> m : maps) {
			JedisUtils.set(m.get("cacheKey").toString(),
					JSONObject.toJSONString(m), 0);
			logger.info("按照省和机场归档key:" + m.get("cacheKey").toString()
					+ " value:" + JSONObject.toJSONString(m));
		}
		return maps;
	}

	@Override
	public List<Map<String, Object>> selectAirportByProvinceIds(
			Map<String, Object> map) {
		Object pids = map.get("pids");
		if (null != pids) {
			List<String> list = Arrays.asList(StringUtils.split(
					pids.toString(), GloabConfig.SPLIT));
			map.put("pids", list);
		}
		List<Map<String, Object>> list = archiveMapper
				.selectAirportByProvinceIds(map);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> m : list) {
			Map<String, Object> rsMap = new HashMap<String, Object>();
			rsMap.put("pid", m.get("pid"));
			rsMap.put("pname", m.get("pname"));
			List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
			if (m.get("aids") == null || m.get("anames") == null) {
				break;
			}
			String[] aidsArrays = StringUtils.split(m.get("aids").toString(),
					GloabConfig.SPLIT);
			String[] anamesArrays = StringUtils.split(m.get("anames")
					.toString(), GloabConfig.SPLIT);
			if (aidsArrays.length != anamesArrays.length) {
				logger.error("根据省Id查询机场信息，机场ids和机场names数量不匹配...");
			} else {
				for (int i = 0; i < aidsArrays.length; i++) {
					Map<String, Object> innerRsMap = new HashMap<String, Object>();
					innerRsMap.put("aids", aidsArrays[i]);
					innerRsMap.put("anames", anamesArrays[i]);
					maps.add(innerRsMap);
				}
				rsMap.put("airPortList", maps);
				result.add(rsMap);
			}
		}
		return result;
	}
}
