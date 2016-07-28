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
	 * baseDailyExtract(订单日基础表数据抽取)
	 * 
	 * @param map
	 *            void
	 * @version 1.0.0
	 */
	void baseDailyExtract(Map<String, Object> map);

	/**
	 * masterHome(经理首页)
	 * 
	 * @param model
	 *            void
	 * @version 1.0.0
	 */
	void masterHome(Model model, User user);

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
	 * 日期+省份缓存：订单数统计和退卡数据统计
	 * 
	 * @param map
	 *            key:currentTime <br>
	 *            value:yyyy-MM-dd
	 * @version 1.0.0
	 */
	void dateProvinceIdCache(Map<String, Object> map);

	/**
	 * dateProvinceIdApportIds(缓存key:date+省Id+场站Id)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	void dateProvinceIdApportIds(Map<String, Object> map);

	/**
	 * 根据省份获取场站
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectAirportByProvinceIds(Map<String, Object> map);

	/**
	 * 每隔30分钟扫描并更新无效订单
	 * 
	 * @param endTime
	 *            void
	 * @version 1.0.0
	 */
	int validateOrdereIsEfectiveAndHadnle(String endTime);
}
