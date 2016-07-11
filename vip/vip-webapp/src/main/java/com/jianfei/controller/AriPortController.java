/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午7:18:26
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.impl.HttpServiceRequest;
import com.jianfei.core.service.base.AriPortManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月18日 上午7:18:26
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping(value = "airport")
public class AriPortController extends BaseController {

	@Autowired
	private AriPortManager<AriPort> ariPortManager;

	@RequiresPermissions(value = "system:station;home")
	@RequestMapping(value = { "", "/home" })
	public String home() {

		return "airport/airportList";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
	@ResponseBody
	public Grid<Map<String, Object>> list(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			@RequestParam(value = "name", required = false) String name,
			HttpServletRequest request) {
		PageHelper.startPage(page, rows);
		// searchParams.put("sort", sortCplumn(request));
		List<Map<String, Object>> maps = ariPortManager
				.mapList(new MapUtils.Builder().setKeyValue("name", name)
						.setKeyValue("dtflag", GloabConfig.OPEN)
						.setKeyValue("order", request.getParameter("order"))
						.setKeyValue("sort", request.getParameter("sort"))
						.build());
		if (!CollectionUtils.isEmpty(maps)) {
			return bindGridData(new PageInfo<Map<String, Object>>(maps));
		}
		return bindGridData(new PageInfo<Map<String, Object>>());
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public MessageDto<AriPort> save(AriPort ariPort) {
		ariPort.setDtflag(GloabConfig.OPEN);
		if (!ariPortManager.validateAirPortExist(new MapUtils.Builder()
				.setKeyValue("name", ariPort.getName()).build())) {
			return new MessageDto<AriPort>().setMsgBody("场站已经存在...");
		}
		return ariPortManager.save(ariPort);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<AriPort> update(AriPort ariPort) {

		if (!ariPortManager.validateAirPortExist(new MapUtils.Builder()
				.setKeyValue("name", ariPort.getName())
				.setKeyValue("id", ariPort.getId()).build())) {
			return new MessageDto<AriPort>().setMsgBody("场站已经存在...");
		}
		ariPort.setDtflag(GloabConfig.OPEN);
		MessageDto<AriPort> messageDto = ariPortManager.update(ariPort);
		return messageDto;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<String> delete(@PathVariable(value = "id") String id) {
		MessageDto<String> messageDto = ariPortManager
				.forbitAirport(new MapUtils.Builder().setKeyValue("id", id)
						.setKeyValue("dtflag", GloabConfig.FORBIT).build());
		return messageDto;
	}

	@RequestMapping(value = "form")
	public String form(String id, Model model) {
		if (!StringUtils.isEmpty(id)) {
			List<Map<String, Object>> list = ariPortManager
					.mapList(new MapUtils.Builder().setKeyValue("id", id)
							.build());
			if (!CollectionUtils.isEmpty(list)) {
				model.addAttribute("ariPort", list.get(0));
			}

		}
		List<Map<String, Object>> maps = ariPortManager
				.selectCityById(new MapUtils.Builder().setKeyValue("pid", "0")
						.build());
		if (!CollectionUtils.isEmpty(maps)) {
			model.addAttribute("citys", maps);
		}
		return "airport/airPortForm";
	}

}
