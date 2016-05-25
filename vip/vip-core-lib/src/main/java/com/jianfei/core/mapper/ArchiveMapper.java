/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午4:36:37
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

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

}
