/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月27日-下午6:18:04
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.service.LogManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月27日 下午6:18:04
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping(value = "/log")
public class LogController extends BaseController {

	@Autowired
	private LogManager logManager;

	/**
	 * list(展示用户列表的数据)
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return Grid
	 * @version 1.0.0
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Grid list(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			HttpServletRequest request) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		PageHelper.startPage(page, rows);
		List<Map<String, Object>> messageDto = logManager.logList(searchParams);
		if (!CollectionUtils.isEmpty(messageDto)) {
			return bindGridData(new PageInfo<Map<String, Object>>(messageDto));
		}
		return bindGridData(new PageInfo<Map<String, Object>>());
	}

	@RequestMapping(value = "/home")
	public String home() {
		return "app/log";
	}
}
