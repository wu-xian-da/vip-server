package com.jianfei.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppUserFeedback;
import com.jianfei.core.bean.Role;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.enu.VipOrderState;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.SortListUtil;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.base.AppUserFeedbackManager;
import com.jianfei.core.service.base.BusizzManager;
import com.jianfei.core.service.order.OrderManager;
import com.jianfei.core.service.stat.ArchiveManager;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private ArchiveManager archiveManager;

	@Autowired
	private OrderManager orderManager;

	@Autowired
	private AppUserFeedbackManager appUserFeedbackManager;
	@Autowired
	private BusizzManager<User> busizzManager;

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
		if (2 == user.getUserType()) {
			return "home/default";
		}
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
		Map<String, Object> map = DateUtil.getDelayDate(1);
		String dateTime = map.get("year") + "-" + map.get("month");
		List<Map<String, Object>> maps = busizzManager
				.selectMap(new MapUtils.Builder()
						.setKeyValue("dateTime", dateTime)
						.setKeyValue("state", "0").build());
		int come = maps == null ? 0 : maps.size();
		model.addAttribute("come", come);
		List<Map<String, Object>> mapsOut = busizzManager
				.selectMap(new MapUtils.Builder()
						.setKeyValue("dateTime", dateTime)
						.setKeyValue("state", "1").build());
		int out = mapsOut == null ? 0 : mapsOut.size();
		model.addAttribute("out", out);
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
		List<Map<String, Object>> list = orderManager
				.selectOrder(new MapUtils.Builder().setKeyValue("orderState",
						VipOrderState.AUDIT_PASS.getName()).build());
		if (!CollectionUtils.isEmpty(list)) {
			model.addAttribute("unHandleOrder", list.size());
		} else {
			model.addAttribute("unHandleOrder", 0);
		}
		PageInfo<AppUserFeedback> pageInfo = appUserFeedbackManager.pageList(0,
				99999999, new MapUtils.Builder()
						.setKeyValue("feedbackState", 0).build());
		model.addAttribute("unfeedBack", pageInfo.getTotal());
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
		List<Map<String, Object>> maps = orderManager
				.selectOrder(new MapUtils.Builder()
						.setKeyValue("dateTime",
								DateUtil.dateToString(new Date(), "yyyy-MM"))
						.setKeyValue("effective", "ok").build());
		if (!CollectionUtils.isEmpty(maps)) {
			model.addAttribute("nowMonthOrder", maps.size());
		} else {
			model.addAttribute("nowMonthOrder", 0);
		}
		List<Map<String, Object>> list = orderManager
				.selectOrder(new MapUtils.Builder().setKeyValue("orderState",
						VipOrderState.AUDIT_PASS.getName()).build());
		if (!CollectionUtils.isEmpty(list)) {
			model.addAttribute("unHandleOrder", list.size());
		} else {
			model.addAttribute("unHandleOrder", 0);
		}
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
		User user = getCurrentUser();
		archiveManager.masterHome(model, user);

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

	@RequestMapping(value = "/droll/home")
	public String busizzDrollPage(String state, Model model) {
		model.addAttribute("state", state);
		System.out.println("**************************************" + state);
		return "user/busizzDroll";
	}

}
