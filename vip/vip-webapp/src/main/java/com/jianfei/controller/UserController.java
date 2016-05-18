/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月13日-上午2:02:32
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.sys.SystemService;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月13日 上午2:02:32
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "home")
	public String home() {

		return "user/Syuser";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Grid list(
			@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			HttpServletRequest request) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		searchParams.put("sort", sortCplumn(request));
		searchParams.put("order", request.getParameter("order"));
		PageHelper.startPage(pageNo, pageSize);
		List<User> list = systemService.getUserMapper().get(searchParams);
		PageInfo<User> pageInfo = new PageInfo<User>(list);
		intiWebContentEnv();
		return systemService.bindUserGridData(pageInfo);
	}

	@RequestMapping(value = "form")
	public String from(User user, Model model) {
		if (0 != user.getId()) {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("id", user.getId());
			List<User> list = systemService.getUserMapper().get(searchParams);
			if (!CollectionUtils.isEmpty(list)) {
				model.addAttribute("user", list.get(0));
			}
		}
		return "user/SyuserForm";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto save(User user) {
		SimpleHash simpleHash = new SimpleHash("md5",
				GloabConfig.getConfig("defalut.passwd"), user.getSalt());
		user.setPassword(simpleHash.toString());
		if (!StringUtils.isEmpty(user.getLoginName())) {
			User u = systemService.getUserMapper().getUserByName(
					StringUtils.trim(user.getLoginName()));
			if (null != u)
				return new MessageDto().setMsgBody("用户名已经存在,请更换用户名...");
		}
		systemService.getUserMapper().save(user);
		return buildDtoMsg(true).setMsgBody("success...");
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto update(User user) {
		user.setLoginName(StringUtils.trim(user.getLoginName()));

		if (!StringUtils.isEmpty(user.getLoginName())) {
			User u = systemService.getUserMapper().getUserByName(
					StringUtils.trim(user.getLoginName()));
			if (null != u && user.getId() != u.getId())
				return new MessageDto().setMsgBody("用户名已经存在,请更换用户名...");
		}
		systemService.getUserMapper().update(user);
		return buildDtoMsg(true).setMsgBody("更新成功...");
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public MessageDto delete(User user) {
		systemService.getUserMapper().delete(user.getId());
		return buildDtoMsg(true);
	}

	@RequestMapping(value = "grant")
	public String grantRole(User user, Model model) {
		model.addAttribute("user", user);
		return "user/userRoleGrant";
	}

	@RequestMapping(value = "grantRole")
	@ResponseBody
	public MessageDto grantRoles(Long id, String ids) {
		return systemService.batchUpdateUserRoles(id, ids);
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

	@RequestMapping(value = "/datapermission/save")
	@ResponseBody
	public MessageDto saveDataPermission(Map<String, Object> map) {
		System.out.println(JSONObject.toJSONString(map));
		System.out.println("refineli....");
		return buildDtoMsg(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);

	}

	@RequestMapping(value = "/datePermission")
	public String datePermission() {
		return "user/permission";
	}

	@RequestMapping(value = "/datapermission/update")
	@ResponseBody
	public MessageDto saveDataPermissionu() {
		return buildDtoMsg(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);

	}
}
