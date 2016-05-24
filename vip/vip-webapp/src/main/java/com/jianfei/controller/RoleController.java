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
		for (Role role : roles) {
			System.out.println(role.getName());
		}
		System.out.println(JSONObject.toJSONString(roles));
		PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
		return bindDataGrid(pageInfo);
	}

	@RequestMapping(value = "form")
	public String form(String id, Model model) {
		List<Role> list = systemService.getRoleMapper().get(
				new MapUtils.Builder().setKeyValue("id", id).build());
		if (!CollectionUtils.isEmpty(list)) {
			model.addAttribute("role", list.get(0));
		}
		return "role/SyroleForm";
	}

	/**
	 * delete(删除角色)
	 * 
	 * @param id
	 *            主键
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public MessageDto<String> delete(Role t) {
		MessageDto<String> dto = new MessageDto<String>();
		try {
			t.setDtflag(1);
			systemService.getRoleMapper().update(t);
		} catch (Exception e) {
			return dto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return dto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/**
	 * save(保存角色信息)
	 * 
	 * @param role
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	@RequestMapping(value = "saveAndgrant")
	@ResponseBody
	public MessageDto<String> save(@RequestParam(value = "id",required=false) Long id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "description",required=false) String description,
			@RequestParam(value = "ids") String ids) {
		return systemService.updateRoleResource(id, name, description, ids);
	}


	/**
	 * tree(角色授权-加载树形资源树)
	 * 
	 * @return List<JsonTreeData>
	 * @version 1.0.0
	 */
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
