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
	Map<String, Object> zhuGuanTotal(Map<String, Object> map,String cacheKey);

	/**
	 * zhuGuanAllAirPort(主管查看管辖区销售情况)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> zhuGuanAllAirPort(Map<String, Object> map,String cacheKey);

	/**
	 * zhuGuanDraw(绘主管管辖区下的销售柱状图)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> zhuGuanDraw(Map<String, Object> map,String cacheKey);
}
