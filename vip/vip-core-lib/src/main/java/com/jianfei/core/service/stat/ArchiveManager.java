/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:14:19
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.jianfei.core.bean.User;

/**
 *
 * @Description: 订单归档，后台根据角色不同，首页展示不同的图表
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午5:14:19
 * 
 * @version 1.0.0
 *
 */
public interface ArchiveManager {

	/**
	 * masterTotal(全国的订单数)
	 * 
	 * @param map
	 *            查询条件
	 * @return Map<String,Object>
	 * @version 1.0.0
	 */
	Map<String, Object> masterTotal(Map<String, Object> map);

	/**
	 * masterTop(top级别的省开卡信息)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> masterTop(Map<String, Object> map);

	/**
	 * masterDraw(为经理角色绘图 )
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> masterDraw(Map<String, Object> map,
			String cacheKey);

	/**
	 * zhuGuanTotal(主管查看指定区域的总的销售量)
	 * 
	 * @param map
	 * @return Map<String,Object>
	 * @version 1.0.0
	 */
	Map<String, Object> zhuGuanTotal(Map<String, Object> map, String cacheKey);

	/**
	 * zhuGuanAllAirPort(主管查看管辖区销售情况)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> zhuGuanAllAirPort(Map<String, Object> map,
			String cacheKey);

	/**
	 * zhuGuanDraw(绘主管管辖区下的销售柱状图)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> zhuGuanDraw(Map<String, Object> map,
			String cacheKey);

	/**
	 * baseDailyExtract(订单日基础表数据抽取)
	 * 
	 * @param map
	 *            void
	 * @version 1.0.0
	 */
	void baseDailyExtract(Map<String, Object> map);

	/**
	 * selectOrderMaxDay(获取最大的订单日期)
	 * 
	 * @return Map<String,Object>
	 * @version 1.0.0
	 */
	Map<String, Object> selectOrderMaxDay();

	/**
	 * masterHome(经理首页)
	 * 
	 * @param model
	 *            void
	 * @version 1.0.0
	 */
	void masterHome(Model model);

	/**
	 * chargeHome(主管首页)
	 * 
	 * @param model
	 * @param user
	 *            void
	 * @version 1.0.0
	 */
	void chargeHome(Model model, User user);

	/******************************************* 分割线 **********************************************************/
	/**
	 * dateProvinceIdRedisCache(缓存key:date+provinceId)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> dateProvinceIdRedisCache(Map<String, Object> map);

	/**
	 * dateProvinceIdApportIds(缓存key:date+省Id+场站Id)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> dateProvinceIdApportIds(Map<String, Object> map);

}
