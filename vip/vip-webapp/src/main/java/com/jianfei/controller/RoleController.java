/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月12日-下午2:52:22
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.Role;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.sys.RoleManager;

/**
 *
 * @Description: 角色管理
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
	private RoleManager roelManager;

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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
	@ResponseBody
	public Grid<Role> list(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			@RequestParam(value = "name", required = false) String name) {
		PageHelper.startPage(page, rows);
		MessageDto<List<Role>> messageDto = roelManager
				.get(new MapUtils.Builder().setKeyValue("name", name).build());
		if (messageDto.isOk()) {
			return bindGridData(new PageInfo<Role>(messageDto.getData()));
		}
		return bindGridData(new PageInfo<Role>());
	}

	@RequestMapping(value = "form")
	public String form(String id, Model model) {
		if (!StringUtils.isEmpty(id)) {
			MessageDto<List<Role>> messageDto = roelManager
					.get(new MapUtils.Builder().setKeyValue("id", id).build());
			if (messageDto.isOk()) {
				model.addAttribute("role", messageDto.getData().get(0));
			}
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
		t.setDtflag(1);
		return roelManager.update(t);
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
	public MessageDto<String> save(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "url") String url,
			@RequestParam(value = "initPwd") String initPwd) {
		return roelManager.updateRoleResource(id, name, description, ids, url,
				initPwd);
	}

}
