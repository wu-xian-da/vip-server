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
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.jianfei.core.bean.Role;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.PasswdHelper;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.sys.RoleManager;
import com.jianfei.core.service.sys.UserManaer;

/**
 *
 * @Description: 用户管理
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
	private UserManaer<User> userManaer;

	@Autowired
	private RoleManager roelManager;

	@Autowired
	private AriPortManager<AriPort> ariPortService;

	@RequiresPermissions(value = "system:user:home")
	@RequestMapping(value = "home")
	public String home() {

		return "user/Syuser";
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
		searchParams.put("dtflag", "0");
		PageHelper.startPage(page, rows);
		MessageDto<List<User>> messageDto = userManaer.get(searchParams);
		if (messageDto.isOk()) {
			return bindGridData(new PageInfo<User>(messageDto.getData()));
		}
		return bindGridData(new PageInfo<User>());
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
			MessageDto<List<User>> messageDto = userManaer.get(searchParams);
			if (messageDto.isOk()) {
				model.addAttribute("user", messageDto.getData().get(0));
				List<Role> roles = roelManager.selectRoleByUserId(messageDto
						.getData().get(0).getId());
				if (!CollectionUtils.isEmpty(roles)) {
					model.addAttribute("selected", roles.get(0));
				}
			}
		}
		MessageDto<List<Role>> roleMessageDto = roelManager
				.get(new MapUtils.Builder().build());
		if (roleMessageDto.isOk()) {
			model.addAttribute("roleSeclect", roleMessageDto.getData());
		}
		List<Map<String, Object>> list = ariPortService
				.datePermissionData(StringUtils.toLong(user.getId()));
		model.addAttribute("datas", list);
		MessageDto<List<Role>> messageDto = roelManager
				.get(new MapUtils.Builder().build());
		if (messageDto.isOk()) {
			model.addAttribute("roles", messageDto.getData());
		}

		return "user/SyuserForm";
	}

	/**
	 * save(保存用户信息)
	 * 
	 * @param user
	 * @return MessageDto<User>
	 * @version 1.0.0
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public MessageDto<String> save(User user, String arids, String roleids) {
		user.setUserType(GloabConfig.SYSTEM_USER);
		return userManaer.saveUser(user, arids, roleids);

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
		return userManaer.delete(user.getId());
	}

	/**
	 * grantRole(授权)
	 * 
	 * @param user
	 * @param model
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping(value = "grant")
	public String grantRole(User user, Model model) {
		model.addAttribute("user", user);
		return "user/userRoleGrant";
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
	 * datePermission(为用户授权数据权限视图)
	 * 
	 * @param id
	 * @param model
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/datePermission")
	public String datePermission(Long id, Model model) {
		model.addAttribute("id", id);
		return "user/permission";
	}

	/**
	 * 重置密码
	 * 
	 * @param orgpwd
	 *            原始密码
	 * @param password
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "resetPasswd")
	@ResponseBody
	public MessageDto<String> initUserPwd(String orgpwd, String password) {
		MessageDto<String> messageDto = new MessageDto<String>();
		User user = getCurrentUser();
		// 判断原始密码是否正确
		String passwd = PasswdHelper.passwdProdece(orgpwd, user.getSalt());
		if (!passwd.equals(user.getPassword())) {
			return messageDto.setMsgBody("原始密码不正确...");
		}
		if (userManaer.resetPasswd(new MapUtils.Builder()
				.setKeyValue("id", user.getId())
				.setKeyValue("password", user.getPassword())
				.setKeyValue("newPassword",
						PasswdHelper.passwdProdece(password, user.getSalt()))
				.setKeyValue("extra_passwd",
						PasswdHelper.passwdProdece(password)).build())) {
			return messageDto.setMsgBody("修改密码成功...").setOk(true);
		}
		return messageDto.setMsgBody("请稍后再试...");
	}
}
