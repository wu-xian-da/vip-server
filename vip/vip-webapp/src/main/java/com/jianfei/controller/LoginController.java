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

import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.Role;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.CacheCons.Sys;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MapUtils;
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

	@RequestMapping(value = { "/", "/main" })
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
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		User user = getCurrentUser();
		List<AriPort> ariPorts = user.getAripors();
		List<String> list = new ArrayList<String>();
		for (AriPort ariPort : ariPorts) {
			list.add(ariPort.getId());
		}
		String airpordIds = StringUtils.join(list, ",");
		lastMoth.put("ariportIds", airpordIds);
		// Map<String, Object> map = archiveManager.zhuGuanTotal(lastMoth);
		model.addAttribute("dataStr", lastMoth.get("dataStr"));
		// model.addAttribute("total", map.get("total"));
		// model.addAttribute("top",
		// archiveManager.zhuGuanAllAirPort(lastMoth));
		model.addAttribute(
				"draw1",
				handDraw(archiveManager.zhuGuanDraw(lastMoth,
						CacheCons.Sys.LAST_1_MONTH), "上月开卡数", lastMoth
						.get("dataStr")));

		model.addAttribute(
				"draw2",
				handDraw(archiveManager.zhuGuanDraw(DateUtil.getDelayDate(2),
						Sys.LAST_2_MONTH), "上月开卡数", DateUtil.getDelayDate(2)
						.get("dataStr")));

		model.addAttribute(
				"draw3",
				handDraw(archiveManager.zhuGuanDraw(DateUtil.getDelayDate(3),
						CacheCons.Sys.LAST_3_MONTH), "上月开卡数", DateUtil
						.getDelayDate(3).get("dataStr")));
		return "home/charge";
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
		Map<String, Object> map = archiveManager
				.masterTotal(new MapUtils.Builder().build());
		model.addAttribute("total", map.get("total"));
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		model.addAttribute("dataStr", lastMoth.get("dataStr"));
		model.addAttribute("top", archiveManager.masterTop(lastMoth));
		model.addAttribute(
				"draw1",
				handDraw(archiveManager.masterDraw(lastMoth,
						CacheCons.Sys.LAST_1_MONTH), "省份/月份开卡数", lastMoth
						.get("dataStr")));

		model.addAttribute(
				"draw2",
				handDraw(archiveManager.masterDraw(DateUtil.getDelayDate(2),
						Sys.LAST_2_MONTH), "省份/月份开卡数", DateUtil.getDelayDate(2)
						.get("dataStr")));

		model.addAttribute(
				"draw3",
				handDraw(archiveManager.masterDraw(DateUtil.getDelayDate(3),
						CacheCons.Sys.LAST_3_MONTH), "省份/月份开卡数", DateUtil
						.getDelayDate(3).get("dataStr")));
		return "home/master";
	}

	public Map<String, String> handDraw(List<Map<String, Object>> draw,
			String text, Object title) {
		Map<Object, Object> drawMap = new HashMap<Object, Object>();
		for (Map<String, Object> m1 : draw) {
			drawMap.put(m1.get("province"), m1.get("order_num"));
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("xAxis", StringUtils.join(drawMap.keySet(), ","));
		map.put("series", StringUtils.join(drawMap.values(), ","));
		map.put("text", text);
		map.put("title", title == null ? "开卡数" : title.toString() + "开卡数");
		return map;
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
