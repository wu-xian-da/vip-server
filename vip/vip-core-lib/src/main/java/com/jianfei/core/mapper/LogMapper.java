/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月27日-下午6:22:27
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: 日志持久层
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月27日 下午6:22:27
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface LogMapper {

	/**
	 * 日志列表
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> mapList(Map<String, Object> map);

}
