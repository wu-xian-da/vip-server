/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月19日-下午1:49:54
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description: 通用接口
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月19日 下午1:49:54
 * 
 * @version 1.0.0
 *
 */
public interface BaseService<T extends Serializable> {
	/**
	 * save(保存机场信息)
	 * 
	 * @param t
	 * @return MessageDto<T>
	 * @version 1.0.0
	 */
	MessageDto<T> save(T t);

	/**
	 * update(更新机场信息)
	 * 
	 * @param t
	 * @return MessageDto<T>
	 * @version 1.0.0
	 */
	MessageDto<T> update(T t);

	/**
	 * get(多纬度查询机场信息)
	 * 
	 * @param params
	 * @return MessageDto<List<T>>
	 * @version 1.0.0
	 */
	MessageDto<List<T>> get(Map<String, Object> params);
	
}
