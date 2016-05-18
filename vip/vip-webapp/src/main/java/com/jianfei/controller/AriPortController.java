/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午7:18:26
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.sys.AriPortService;

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
	private AriPortService ariPortService;

	@RequestMapping(value = { "", "/home" })
	public String home() {

		return "airport/airportList";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Grid<AriPort> list(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			@RequestParam(value = "name", required = false) String name) {
		PageHelper.startPage(page, rows);
		List<AriPort> ariPorts = ariPortService.getAriPortMapper().get(
				new MapUtils.Builder().setKeyValue("name", name).build());

		PageInfo<AriPort> pageInfo = new PageInfo<AriPort>(ariPorts);

		return bindDataGrid(pageInfo);
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public MessageDto save(AriPort ariPort) {
		System.out.println(ariPort.getId().length());
		List<AriPort> list = ariPortService.getAriPortMapper().get(
				new MapUtils.Builder().setKeyValue("name", ariPort.getName())
						.build());
		if (!CollectionUtils.isEmpty(list)) {
			return new MessageDto().setMsgBody("场站已经存在...");
		}
		ariPortService.getAriPortMapper().save(ariPort);
		return buildDtoMsg(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	@RequestMapping(value = "form")
	public String form(String id, Model model) {
		if (!StringUtils.isEmpty(id)) {
			List<AriPort> ariPorts = ariPortService.getAriPortMapper().get(
					new MapUtils.Builder().setKeyValue("id", id).build());
			if (!CollectionUtils.isEmpty(ariPorts)) {
				model.addAttribute("ariPort", ariPorts.get(0));
			}
		}
		return "airport/airPortForm";
	}


	@RequestMapping(value = "/datePermission/data", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> datePermissionData() {
		List<AriPort> ariPorts = ariPortService.getAriPortMapper().get(
				new MapUtils.Builder().build());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (AriPort ariPort : ariPorts) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", ariPort.getId());
			map.put("name", ariPort.getName());
			map.put("checked", false);
			list.add(map);
		}
		return list;
	}
}
