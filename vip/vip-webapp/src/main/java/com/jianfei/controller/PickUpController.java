package com.jianfei.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.common.enu.PickUpType;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.base.AppAirportTransManager;

/**
 *
 * @Description: 接送机控制类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月6日 上午10:51:01
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping(value = "/pickup")
public class PickUpController extends BaseController {

	@Autowired
	private AppAirportTransManager appAirportTransManager;

	@RequestMapping(value = "home")
	public String home() {

		return "app/pickupList";
	}

	/**
	 * 接送机根据条件分页查询
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @return
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
		searchParams.put("sort", request.getParameter("sort"));
		searchParams.put("order", request.getParameter("order"));
		PageHelper.startPage(page, rows);
		MessageDto<List<Map<String, Object>>> messageDto = appAirportTransManager
				.resultMapList(searchParams);
		if (messageDto.isOk()) {
			return bindGridData(new PageInfo<Map<String, Object>>(
					messageDto.getData()));
		}
		return bindGridData(new PageInfo<Map<String, Object>>());
	}

	/**
	 * 更新接送操作的状态 (接送，未接送)
	 * 
	 * @param submit
	 *            1:未接送<br>
	 *            2:已接送
	 * @param id
	 *            主键
	 * @return
	 */
	@RequestMapping(value = "/submit/{id}")
	public String look(@PathVariable("id") String id, Model model) {
		model.addAttribute("pk", appAirportTransManager.pickupLook(id));
		return "app/pickupForm";
	}

	/**
	 * updateState(更新接送机状态)
	 * 
	 * @param id
	 *            主键Id
	 * @return String
	 * @version 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateState/{id}")
	public MessageDto<String> updateState(@PathVariable("id") String id) {
		MessageDto<String> messageDto = appAirportTransManager
				.updateState(new MapUtils.Builder().setKeyValue("id", id)
						.setKeyValue("submit", PickUpType.submited.getType())
						.build());
		return messageDto;
	}
}
