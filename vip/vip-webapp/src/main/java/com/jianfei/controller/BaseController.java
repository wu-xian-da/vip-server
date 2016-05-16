/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月11日-下午12:59:36
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jianfei.core.common.shrio.ShrioUser;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MessageDto;

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
	public MessageDto buildDtoMsg(boolean isOk) {
		MessageDto dto = new MessageDto();
		if (isOk) {
			dto.setOk(true);
		}
		return dto;
	}

	public void intiWebContentEnv() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		request.setAttribute("uploadRoot",
				GloabConfig.getConfig("static.resource.server.address"));
		request.setAttribute("webRoot", getBaseUrl(request));
	}

	public ShrioUser getShrioUser() {
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		if (null != subject && null != subject.getPrincipal()) {
			return (ShrioUser) subject.getPrincipal();
		}
		return null;
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
