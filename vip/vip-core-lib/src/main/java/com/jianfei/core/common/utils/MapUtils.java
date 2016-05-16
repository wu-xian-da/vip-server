/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午6:40:49
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @Description: map工具类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月16日 下午3:41:54
 * 
 * @version 1.0.0
 *
 */
public class MapUtils extends org.apache.commons.collections.MapUtils {

	/**
	 * 对象转Map
	 * 
	 * @param object
	 *            目标对象
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object object)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return BeanUtils.describe(object);
	}

	static ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> entityInitMap(T t) {
		String json;
		try {
			json = mapper.writeValueAsString(t);
			return mapper.readValue(json, HashMap.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static class Build {

		public Map<String, Object> map = new HashMap<String, Object>();

		public Build setKeyValue(String key, Object value) {
			if (!StringUtils.isEmpty(key) && value != null
					&& !StringUtils.isEmpty(value.toString()))
				map.put(key, value);
			return this;
		}

		public Map<String, Object> build() {
			return map;
		}

	}
}
