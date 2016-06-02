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

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
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
			@RequestParam(value = "name", required = false) String name) {
		PageHelper.startPage(page, rows);
		List<Map<String, Object>> maps = ariPortManager
				.mapList(new MapUtils.Builder().setKeyValue("name", name)
						.setKeyValue("dtflag", GloabConfig.OPEN).build());
		if (!CollectionUtils.isEmpty(maps)) {
			return bindGridData(new PageInfo<Map<String, Object>>(maps));
		}
		return bindGridData(new PageInfo<Map<String, Object>>());
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public MessageDto<AriPort> save(AriPort ariPort) {
		Map<String, String> map = com.jianfei.core.common.utils.StringUtils
				.selectCity(ariPort.getProvince());
		ariPort.setProvince(map.get("provice"));
		ariPort.setCity(map.get("city"));
		ariPort.setCountry(map.get("country"));
		System.out.println(JSONObject.toJSONString(ariPort));
		MessageDto<List<AriPort>> messageDto = ariPortManager
				.get(new MapUtils.Builder().setKeyValue("name",
						ariPort.getName()).build());
		if (messageDto.isOk() && !CollectionUtils.isEmpty(messageDto.getData())) {
			return new MessageDto<AriPort>().setMsgBody("场站已经存在...");
		}
		return ariPortManager.save(ariPort);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<AriPort> update(AriPort ariPort) {
		MessageDto<AriPort> messageDto = ariPortManager.update(ariPort);
		return messageDto;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<AriPort> delete(AriPort ariPort) {
		ariPort.setDtflag(GloabConfig.FORBIT);
		MessageDto<AriPort> messageDto = ariPortManager.update(ariPort);
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
		return "airport/airPortForm";
	}

	@RequestMapping(value = "selectCity")
	@ResponseBody
	public List<Map<String, Object>> selectCitys(String pid) {
		return ariPortManager.selectCityById(new MapUtils.Builder()
				.setKeyValue("pid", pid).build());
	}

	@RequestMapping(value = "ss")
	public String sss(String pid) {
		return "airport/master";
	}
}
