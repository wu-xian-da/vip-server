/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午3:44:19
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月15日 下午3:44:19
 * 
 * @version 1.0.0
 *
 */
public interface BaseMapper<T> {

	void save(T t);

	void batchInsert(List<Map<String, Object>> list);

	void update(T t);

	void delete(Long id);

	List<T> get(Map<String, Object> params);

	T findEntityById(Long id);
}
