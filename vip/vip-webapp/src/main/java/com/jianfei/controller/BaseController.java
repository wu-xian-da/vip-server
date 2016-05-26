/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月11日-下午12:59:36
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.security.shiro.ShiroUtils;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月11日 下午12:59:36
 * 
 * @version 1.0.0
 *
 */
public class BaseController {

	@SuppressWarnings("rawtypes")
	public <T> Grid bindGridData(PageInfo<T> pageInfo) {
		List<T> list = pageInfo.getList();
		if (CollectionUtils.isEmpty(list)) {
			list = Lists.newArrayList();
		}
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (T user : list) {
			Map<String, Object> map = MapUtils.<T> entityInitMap(user);
			maps.add(map);
		}
		Grid<Map<String, Object>> grid = new Grid<Map<String, Object>>();
		grid.setRows(maps);
		grid.setTotal(pageInfo.getTotal());
		return grid;
	}

	public User getCurrentUser() {
		return (User) ShiroUtils.getSession().getAttribute(
				GloabConfig.SESSION_USER);
	}

	public void intiWebContentEnv() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		request.setAttribute("uploadRoot",
				GloabConfig.getConfig("static.resource.server.address"));
		request.setAttribute("webRoot", getBaseUrl(request));
	}

	/**
	 * 获取应用上下文路径
	 * 
	 * @param request
	 * @return String
	 * @version 1.0.0
	 */
	public String getBaseUrl(HttpServletRequest request) {
		StringBuilder baseUrl = new StringBuilder();

		baseUrl.append(request.getScheme());
		baseUrl.append("://");
		baseUrl.append(request.getServerName());
		baseUrl.append(":");
		baseUrl.append(request.getServerPort());
		baseUrl.append(request.getContextPath());
		baseUrl.append("/");

		return baseUrl.toString();
	}
}
