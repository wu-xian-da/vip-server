/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月12日-下午2:52:22
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.Role;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.JsonTreeData;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.sys.SystemService;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月12日 下午2:52:22
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping(value = "role")
public class RoleController extends BaseController {

	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "/home")
	public String home() {
		return "role/roles";
	}

	/**
	 * list(分页展示角色列表)
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return Grid
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Grid<Role> list(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			@RequestParam(value = "name", required = false) String name) {
		PageHelper.startPage(page, rows);
		List<Role> roles = systemService.getRoleMapper().get(
				new MapUtils.Builder().setKeyValue("name", name).build());
		System.out.println(JSONObject.toJSONString(roles));
		PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
		return bindDataGrid(pageInfo);
	}

	@RequestMapping(value = "form")
	public String form() {
		return "role/SyroleForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public MessageDto save(Role role) {
		List<Role> list = systemService.getRoleMapper().get(
				new MapUtils.Builder().setKeyValue("name", role.getName())
						.build());
		if (CollectionUtils.isEmpty(list)) {
			return new MessageDto().setMsgBody("同名的角色已经存在，请更换名字...");
		}
		systemService.getRoleMapper().save(role);
		return buildDtoMsg(true);
	}

	@RequestMapping(value = "grantForm")
	public String grantFrom(Role role, Model model) {
		model.addAttribute("id", role.getId());
		return "role/SyroleGrant";
	}

	@RequestMapping(value = "grant", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto grant(@RequestParam(value = "id") Long id,
			@RequestParam(value = "ids") String ids) {
		return systemService.updateRoleResource(id, ids);
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<JsonTreeData> tree() {
		return systemService.buildRoleTreeNode();
	}

	@RequestMapping(value = "selectroles/{id}")
	@ResponseBody
	public List<Role> selectRolesByUserId(@PathVariable("id") Long id) {
		return systemService.getRoleMapper().selectRoleByUserId(id);
	}

}
