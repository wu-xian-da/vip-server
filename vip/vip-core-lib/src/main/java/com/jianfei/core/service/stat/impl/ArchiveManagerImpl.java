/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:15:27
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.RoleType;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.ObjectUtils;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.mapper.ArchiveMapper;
import com.jianfei.core.service.stat.ArchiveManager;
import com.jianfei.core.service.sys.RoleManager;

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
	private RoleManager roleManager;

	protected Logger logger = LoggerFactory.getLogger(getClass());

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
		archiveMapper.baseDailyBackExtract(map);
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
			buffer.append("'" + ariPort.getId() + "',");
		}
		System.out.println(buffer.toString());
		if (CollectionUtils.isEmpty(list)) {
			model.addAttribute("error", "true");
			return;
		}
		// 查询管辖区域内总的开卡数
		Map<String, Object> map = archiveMapper
				.leaderLookTotal(new MapUtils.Builder().setKeyValue(
						"ariportIds", list).build());
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
		List<Map<String, Object>> top3 = archiveMapper.masterTop(lastMoth);
		model.addAttribute("top", top3);
		// 柱状图报表数据
		model.addAttribute("draw1",
				handDraw(lastMoth, "省份/月份开卡数", lastMoth.get("dataStr")));// 上个月各个省份的开卡数

		Map<String, Object> lastMoth2 = DateUtil.getDelayDate(2);
		lastMoth2.put("ariportIds", list);
		model.addAttribute(
				"draw2",
				handDraw(lastMoth2, "省份/月份开卡数",
						DateUtil.getDelayDate(2).get("dataStr")));// 上上个月各个省份开卡数

		Map<String, Object> lastMoth3 = DateUtil.getDelayDate(3);
		lastMoth3.put("ariportIds", list);
		model.addAttribute(
				"draw3",
				handDraw(lastMoth3, "省份/月份开卡数",
						DateUtil.getDelayDate(3).get("dataStr")));// 上上上个月各个省份的开卡数

	}

	/*
	 * 主管 (non-Javadoc)
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
		Map<String, Object> map = archiveMapper.leaderLookTotal(lastMoth);

		model.addAttribute("dataStr", lastMoth.get("dataStr"));
		if (MapUtils.isEmpty(map)) {
			model.addAttribute("total", 0);
		} else {
			model.addAttribute("total", map.get("total"));
		}
		// 上个月管辖区域内的开卡数
		model.addAttribute("airPorts",
				archiveMapper.zhuGuanAllAirPort(lastMoth));
		// 管辖区域内每个场站每个业务员订单统计
		List<Map<String, Object>> mapList = archiveMapper.zhuGuanDraw(lastMoth);
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> m : mapList) {
			Map<String, Object> mb = new HashMap<String, Object>();
			String xAxis = StringUtils.obj2String(m.get("bnames"));
			mb.put("text", m.get("airportname") + ":横轴业务员,纵轴上月开卡数");
			mb.put("xAxis", xAxis.split(","));
			mb.put("series",
					StringUtils.stringArray2Long(
							StringUtils.obj2String(m.get("order_nums")), ","));
			maps.add(mb);
		}
		model.addAttribute("draw", JSONObject.toJSONString(maps));
	}

	/**
	 * 经理首页，绘制图表数据
	 * 
	 * @param mapCons
	 *            key:ariportIds value:场站ids<br>
	 *            key:year value:yyyy<br>
	 *            key:month value:MM<br>
	 *            key:dataStr value:yyyy年MM月<br>
	 * @param cacheKey
	 * @param text
	 * @param title
	 * @return Map<String,String>
	 * @version 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> handDraw(Map<String, Object> mapCons,
			String text, Object title) {

		List<Map<String, Object>> maps = null;
		try {
			maps = archiveMapper.masterDraw(mapCons);
		} catch (Exception e) {
			logger.error("经理首页，从缓存获取数据失败....");
		}
		Map<Object, Object> drawMap = new HashMap<Object, Object>();
		for (Map<String, Object> m1 : maps) {
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
	public void dateProvinceIdCache(Map<String, Object> map) {

		Set<String> cacheKeys = new HashSet<String>();
		// 指定日期下的订单统计
		List<Map<String, Object>> orderMaps = archiveMapper
				.dateProvinceIdOderCache(map);
		// 指定日期下的退卡数统计
		List<Map<String, Object>> backOrderMaps = archiveMapper
				.dateProvinceIdOderBackCache(map);
		if (CollectionUtils.isEmpty(orderMaps)) {
			orderMaps = new ArrayList<Map<String, Object>>();
		}
		// 判断退卡数是否为空
		if (CollectionUtils.isEmpty(backOrderMaps)) {
			backOrderMaps = new ArrayList<Map<String, Object>>();
		}

		Map<String, Map<String, Object>> orderMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> m : orderMaps) {
			String cacheKey = StringUtils.obj2String(m.get("cacheKey"));
			cacheKeys.add(cacheKey);
			orderMap.put(
					cacheKey,
					new MapUtils.Builder()
							.setKeyValue("pcount", m.get("pcount"))
							.setKeyValue("pid", m.get("pid"))
							.setKeyValue("total", m.get("total"))
							.setKeyValue("avgNum", m.get("avgNum")).build());
		}

		Map<String, Map<String, Object>> backOrderMap = new HashMap<String, Map<String, Object>>();

		for (Map<String, Object> m : backOrderMaps) {

			String cacheKey = StringUtils.obj2String(m.get("cacheKey"));
			cacheKeys.add(cacheKey);
			backOrderMap.put(
					cacheKey,
					new MapUtils.Builder()
							.setKeyValue("pcount", m.get("pcount"))
							.setKeyValue("pid", m.get("pid"))
							.setKeyValue("back_order_total",
									m.get("back_order_total"))
							.setKeyValue("avgNum_back", m.get("avgNum_back"))
							.build());
		}
		for (String cacheKey : cacheKeys) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("cacheKey", cacheKey);
			if (orderMap.containsKey(cacheKey)) {
				result.put("pcount", orderMap.get(cacheKey).get("pcount"));
				result.put("pid", orderMap.get(cacheKey).get("pid"));
			} else {
				result.put("pcount", backOrderMap.get(cacheKey).get("pcount"));
				result.put("pid", backOrderMap.get(cacheKey).get("pid"));
			}
			result.put("total", getValue(cacheKey, orderMap, "total"));
			result.put("avgNum", getValue(cacheKey, orderMap, "avgNum"));
			result.put("back_order_total",
					getValue(cacheKey, backOrderMap, "back_order_total"));
			result.put("avgNum_back",
					getValue(cacheKey, backOrderMap, "avgNum_back"));
			JedisUtils.setObject(cacheKey, JSONObject.toJSONString(result), 0);
		}
	}

	public Object getValue(String cacheKey,
			Map<String, Map<String, Object>> map, String key) {
		if (map.containsKey(cacheKey)) {
			return map.get(cacheKey).get(key);
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#dateProvinceIdApportIds(
	 * java.util.Map)
	 */
	@Override
	public void dateProvinceIdApportIds(Map<String, Object> map) {
		Set<String> cacheKeys = new HashSet<String>();
		// 日期+省份+场站（订单数）
		List<Map<String, Object>> orderMaps = archiveMapper
				.dateProvinceIdApportIds(map);
		// 日期+省份+场站（退卡数）
		List<Map<String, Object>> backOrderMaps = archiveMapper
				.dateProvinceIdApportIdsBack(map);
		if (CollectionUtils.isEmpty(orderMaps)) {
			orderMaps = new ArrayList<Map<String, Object>>();
		}
		if (CollectionUtils.isEmpty(backOrderMaps)) {
			backOrderMaps = new ArrayList<Map<String, Object>>();
		}
		Map<String, Map<String, Object>> orderMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> m : orderMaps) {
			String cacheKey = StringUtils.obj2String(m.get("cacheKey"));
			cacheKeys.add(cacheKey);
			orderMap.put(
					cacheKey,
					new MapUtils.Builder()
							.setKeyValue("pcount", m.get("pcount"))
							.setKeyValue("pid", m.get("pid"))
							.setKeyValue("total", m.get("total"))
							.setKeyValue("avgNum", m.get("avgNum")).build());
		}
		Map<String, Map<String, Object>> backOrderMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> m : backOrderMaps) {

			String cacheKey = StringUtils.obj2String(m.get("cacheKey"));
			cacheKeys.add(cacheKey);
			backOrderMap.put(
					cacheKey,
					new MapUtils.Builder()
							.setKeyValue("pcount", m.get("pcount"))
							.setKeyValue("pid", m.get("pid"))
							.setKeyValue("back_order_total",
									m.get("back_order_total"))
							.setKeyValue("avgNum_back", m.get("avgNum_back"))
							.build());
		}
		for (String cacheKey : cacheKeys) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("cacheKey", cacheKey);
			if (orderMap.containsKey(cacheKey)) {
				result.put("pcount", orderMap.get(cacheKey).get("pcount"));
				result.put("pid", orderMap.get(cacheKey).get("pid"));
			} else {
				result.put("pcount", backOrderMap.get(cacheKey).get("pcount"));
				result.put("pid", backOrderMap.get(cacheKey).get("pid"));
			}
			result.put("total", getValue(cacheKey, orderMap, "total"));
			result.put("avgNum", getValue(cacheKey, orderMap, "avgNum"));
			result.put("back_order_total",
					getValue(cacheKey, backOrderMap, "back_order_total"));
			result.put("avgNum_back",
					getValue(cacheKey, backOrderMap, "avgNum_back"));
			JedisUtils.setObject(cacheKey, JSONObject.toJSONString(result), 0);
		}
	}

	@Override
	public List<Map<String, Object>> selectAirportByProvinceIds(
			Map<String, Object> map) throws IllegalArgumentException {
		
		Object userNo = map.get("code");

		if (StringUtils.isEmpty(StringUtils.obj2String(userNo))) {
			throw new IllegalArgumentException("工号不能为空....");
		}
		List<Map<String, Object>> listMap = roleManager
				.selectRoleByUserUno(userNo.toString());
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 判断是不是经理
		if (!CollectionUtils.isEmpty(listMap)) {
			Map<String, Object> mapRole = listMap.get(0);
			if (RoleType.managerType.getType().equals(
					StringUtils.obj2String(mapRole.get("role_type")))) {
				Map<String, Object> mAllp = new HashMap<String, Object>();
				mAllp.put("pname", "所有省份");
				mAllp.put("pid", StringUtils.EMPTY);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Map<String, Object> inner = new HashMap<String, Object>();
				inner.put("anames", "所有场站");
				inner.put("aids", StringUtils.EMPTY);
				list.add(inner);
				mAllp.put("airPortList", list);
				result.add(mAllp);
			}
		}
		Object cid = map.get("cid");
		List<Map<String, Object>> list = archiveMapper
				.selectAirportByProvinceIds(new MapUtils.Builder()
						.setKeyValue("code", userNo).setKeyValue("cid", cid)
						.build());

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
				// 判断是不是经理
				if (!CollectionUtils.isEmpty(listMap)) {
					Map<String, Object> mapRole = listMap.get(0);
					if (RoleType.managerType.getType().equals(
							StringUtils.obj2String(mapRole.get("role_type")))
							|| RoleType.masterType.getType().equals(
									StringUtils.obj2String(mapRole
											.get("role_type")))) {
						Map<String, Object> innerRsMap = new HashMap<String, Object>();
						innerRsMap.put("aids", m.get("aids").toString());
						innerRsMap.put("anames", "所有场站");
						maps.add(innerRsMap);
					}
				}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.stat.ArchiveManager#
	 * validateOrdereIsEfectiveAndHadnle(java.lang.String)
	 */
	@Override
	public int validateOrdereIsEfectiveAndHadnle(String endTime) {
		List<Map<String, Object>> maps = archiveMapper
				.validateOrdereIsEfective(endTime);
		ArrayList<String> list = new ArrayList<String>();
		for (Map<String, Object> map : maps) {
			if (!ObjectUtils.isEmpty(map.get("order_id"))) {
				list.add(map.get("order_id").toString());
			}
		}
		if (!CollectionUtils.isEmpty(list)) {
			return archiveMapper.updateOrderStatuIsValidate(list);
		}
		return 0;
	}
}
