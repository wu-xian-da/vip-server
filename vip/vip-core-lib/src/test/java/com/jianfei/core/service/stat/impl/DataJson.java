/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月13日-上午11:45:44
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月13日 上午11:45:44
 * 
 * @version 1.0.0
 *
 */
public class DataJson {

	public static void main(String[] args) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "kitty");
		list.add(map);
		System.out.println(JSONObject.toJSONString(list));
	}

}
