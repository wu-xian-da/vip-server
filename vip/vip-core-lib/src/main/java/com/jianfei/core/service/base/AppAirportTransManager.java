/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月5日-下午3:01:06
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import java.util.List;
import java.util.Map;

import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description:接送机业务层接口
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月5日 下午3:01:06
 * 
 * @version 1.0.0
 *
 */
public interface AppAirportTransManager {

	/**
	 * resultMapList(根据查询条件获取接送机信息)
	 * 
	 * @param searchParams
	 * @return MessageDto<List<Map<String, Object>>>
	 * @version 1.0.0
	 */
	public MessageDto<List<Map<String, Object>>> resultMapList(
			Map<String, Object> searchParams);

	/**
	 * updateState(更改接送机状态)
	 * 
	 * @param params
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	public MessageDto<String> updateState(Map<String, Object> params);

	/**
	 * pickupLook(查看接送机详情)
	 * 
	 * @param id
	 *            接送机主键
	 * @version 1.0.0
	 */
	Map<String, Object> pickupLook(String id);
}
