package com.jianfei.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jianfei.core.bean.Role;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.SortListUtil;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.stat.ArchiveManager;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private ArchiveManager archiveManager;

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request) throws Exception {
		return "login";
	}

	@RequestMapping(value = { "/main" })
	public String main() {
		return "main";
	}

	@RequestMapping(value = "/index")
	public String index(Model model) {
		User user = getCurrentUser();
		List<Role> roles = user.getRoles();
		if (!CollectionUtils.isEmpty(roles)) {
			// 查找优先级最大的角色和首页路径
			SortListUtil.sort(roles, "priority", SortListUtil.DESC);
			String url = roles.get(0).getUrl();
			if (!StringUtils.isEmpty(url)) {
				return "redirect:/" + url;
			}
		}
		return "home/default";
	}

	/**
	 * charge(主管)
	 * 
	 * @param model
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/charge/home")
	public String charge(Model model) {
		User user = getCurrentUser();
		archiveManager.chargeHome(model, user);
		return "home/charge";
	}

	/**
	 * 人力
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/hr/home")
	public String hr(Model model) {
		User user = getCurrentUser();
		archiveManager.chargeHome(model, user);
		return "home/hr";
	}

	/**
	 * 客服
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/service/home")
	public String servicer(Model model) {
		User user = getCurrentUser();
		archiveManager.chargeHome(model, user);
		return "home/service";
	}

	/**
	 * 财务
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/finance/home")
	public String finance(Model model) {
		User user = getCurrentUser();
		archiveManager.chargeHome(model, user);
		return "home/finance";
	}

	/**
	 * master(经理)
	 * 
	 * @param model
	 * @return String
	 * @version 1.0.0
	 */
	@RequestMapping(value = "/master/home")
	public String master(Model model) {
		archiveManager.masterHome(model);

		return "home/master";
	}

	@RequestMapping(value = "layout/header")
	public String header() {
		return "layout/header";
	}

	@RequestMapping(value = "layout/south")
	public String south() {
		return "layout/south";
	}
}
