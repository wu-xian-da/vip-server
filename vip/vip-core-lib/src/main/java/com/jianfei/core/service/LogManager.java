/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月27日-下午6:21:04
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service;

import java.util.List;
import java.util.Map;

/**
 *
 * @Description: 日志管理
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月27日 下午6:21:04
 * 
 * @version 1.0.0
 *
 */
public interface LogManager {

	List<Map<String, Object>> logList(Map<String, Object> map);
}
