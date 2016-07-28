/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午4:36:37
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午4:36:37
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface ArchiveMapper {

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
	List<Map<String, Object>> masterDraw(Map<String, Object> map);

	/**
	 * zhuGuanTotal(主管查看指定区域的总的销售量)
	 * 
	 * @param map
	 * @return Map<String,Object>
	 * @version 1.0.0
	 */
	Map<String, Object> zhuGuanTotal(Map<String, Object> map);

	/**
	 * zhuGuanAllAirPort(主管查看管辖区销售情况)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> zhuGuanAllAirPort(Map<String, Object> map);

	/**
	 * zhuGuanDraw(绘主管管辖区下的销售柱状图)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> zhuGuanDraw(Map<String, Object> map);

	/**
	 * baseDailyExtract(订单日基础表数据抽取)
	 * 
	 * @param map
	 *            void
	 * @version 1.0.0
	 */
	void baseDailyExtract(Map<String, Object> map);

	/**
	 * baseDailyExtract(退卡日基础表数据抽取)
	 * 
	 * @param map
	 *            void
	 * @version 1.0.0
	 */
	void baseDailyBackExtract(Map<String, Object> map);

	/******************************************* 分割线 **********************************************************/
	/**
	 * 按照日期和省份id对订单数统计并放入缓存
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> dateProvinceIdOderCache(Map<String, Object> map);

	/**
	 * 按照日期和省份id对退卡数统计并放入缓存
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> dateProvinceIdOderBackCache(
			Map<String, Object> map);

	/**
	 * 按照日期和省份id和机场id对订单数统计并放入缓存
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> dateProvinceIdApportIds(Map<String, Object> map);

	/**
	 * 按照日期和省份id和机场id对退卡数统计并放入缓存
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> dateProvinceIdApportIdsBack(
			Map<String, Object> map);

	/**
	 * 根据省份获取场站
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectAirportByProvinceIds(Map<String, Object> map);

	/**
	 * 验证订单的有效性，30分钟验证一次
	 * 
	 * @version 1.0.0
	 */
	List<Map<String, Object>> validateOrdereIsEfective(String endTime);

	/**
	 * 更新订单状态为无效状态
	 * 
	 * @param arrayList
	 *            订单id集合
	 * @return int
	 * @version 1.0.0
	 */
	int updateOrderStatuIsValidate(ArrayList<String> arrayList);

}
