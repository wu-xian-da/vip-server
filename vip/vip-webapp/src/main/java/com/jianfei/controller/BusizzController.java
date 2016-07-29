/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午3:58:31
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.base.BusizzManager;

/**
 *
 * @Description: 业务员控制器
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月23日 下午3:58:31
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping(value = "busizz")
public class BusizzController extends BaseController {

	@Autowired
	private BusizzManager<User> busizzManager;

	@Autowired
	private AriPortManager<AriPort> ariPortManager;

	@RequestMapping(value = "home")
	public String home() {

		return "user/busizz";
	}

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
		searchParams.put("sort", sortCplumn(request));
		searchParams.put("order", request.getParameter("order"));
		searchParams.put("today", DateUtil.dateToString(new Date(), "yyyy-MM"));
		searchParams.put("name", request.getParameter("name"));
		Map<String, Object> map = DateUtil.getDelayDate(1);
		searchParams.put("year", map.get("year"));
		searchParams.put("month", map.get("month"));
		PageHelper.startPage(page, rows);
		List<Map<String, Object>> list = busizzManager.listMap(searchParams);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(
				list);
		return bindGridData(pageInfo);
	}

	/**
	 * save(保存业务员信息)
	 * 
	 * @param user
	 * @return MessageDto<User>
	 * @version 1.0.0
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<String> save(User user, String arids, String roleids) {
		user.setUserType(GloabConfig.BUSSNISS_USER);
		return busizzManager.saveUser(user, arids, roleids);

	}

	/**
	 * from(用户添加、更新视图)
	 * 
	 * @param user
	 * @param model
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping(value = "form")
	public String from(User user, Model model) {
		if (0 != user.getId()) {

			List<Map<String, Object>> list = busizzManager
					.selectMap(new MapUtils.Builder().setKeyValue("id",
							user.getId()).build());
			if (!CollectionUtils.isEmpty(list)) {
				model.addAttribute("user", list.get(0));
			}
		}
		List<Map<String, Object>> list = ariPortManager
				.datePermissionData(StringUtils.toLong(user.getId()));
		model.addAttribute("datas", list);
		return "user/busizzForm";
	}

	/**
	 * sortCplumn(这里用一句话描述这个方法的作用)
	 * 
	 * @param request
	 *            void
	 * @version 1.0.0
	 */
	private String sortCplumn(HttpServletRequest request) {
		String sort = request.getParameter("sort");
		if (!StringUtils.isEmpty(sort)) {
			if ("loginName".equals(sort)) {
				sort = "login_name";
			}
			if ("createdatetime".equals(sort)) {
				sort = "create_time";
			}
			if ("updatedatetime".equals(sort)) {
				sort = "update_time";
			}
		}

		return sort;
	}

	@RequestMapping(value = "initPwd")
	@ResponseBody
	public MessageDto<String> initPwd(String id, String salt, String roleId) {
		return busizzManager.initpwd(new MapUtils.Builder()
				.setKeyValue("id", id).setKeyValue("salt", salt)
				.setKeyValue("roleId", roleId).build());
	}

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
	@RequestMapping(value = "/droll", method = RequestMethod.POST)
	@ResponseBody
	public Grid droll(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			String state,String name) {
		Map<String, Object> map = DateUtil.getDelayDate(1);
		String dateTime = map.get("year") + "-" + map.get("month");
		PageHelper.startPage(page, rows);
		List<Map<String, Object>> list = busizzManager
				.selectMap(new MapUtils.Builder()
						.setKeyValue("dateTime", dateTime)
						.setKeyValue("name", name)
						.setKeyValue("state", state).build());
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(
				list);
		return bindGridData(pageInfo);
	}

}
