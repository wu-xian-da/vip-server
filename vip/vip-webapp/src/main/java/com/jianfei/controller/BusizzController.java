/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午3:58:31
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.ArrayList;
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
			@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			HttpServletRequest request) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		searchParams.put("sort", sortCplumn(request));
		searchParams.put("order", request.getParameter("order"));
		PageHelper.startPage(pageNo, pageSize);
		MessageDto<List<User>> messageDto = busizzManager
				.get(searchParams);
		PageInfo<User> pageInfo = new PageInfo<User>(messageDto.getData());
		return bindUserGridData(pageInfo);
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
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("id", user.getId());
			MessageDto<List<User>> messageDto = busizzManager
					.get(new MapUtils.Builder().setKeyValue("id", user.getId())
							.build());
			if (messageDto.isOk()
					|| CollectionUtils.isEmpty(messageDto.getData())) {
				model.addAttribute("user", messageDto.getData().get(0));
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

	/**
	 * delete(删除用户信息)
	 * 
	 * @param user
	 * @return MessageDto<User>
	 * @version 1.0.0
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public MessageDto<String> delete(User user) {
		busizzManager.delete(user.getId());
		return busizzManager.delete(user.getId());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Grid bindUserGridData(PageInfo<User> pageInfo) {
		List<User> list = pageInfo.getList();
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (User user : list) {
			Map<String, Object> map = MapUtils.<User> entityInitMap(user);
			maps.add(map);
		}
		Grid grid = new Grid();
		grid.setRows(maps);
		grid.setTotal(pageInfo.getTotal());
		return grid;
	}

}
