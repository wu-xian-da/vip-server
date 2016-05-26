package com.jianfei.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.stat.ArchiveManager;

@Controller
public class LoginController {

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

		Map<String, Object> map = archiveManager
				.masterTotal(new MapUtils.Builder().build());
		model.addAttribute("total", map.get("total"));
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		model.addAttribute("dataStr",
				lastMoth.get("year") + "年" + lastMoth.get("month") + "月");
		model.addAttribute("top",
				archiveManager.masterTop(new MapUtils.Builder().build()));
		model.addAttribute("draw1",
				handDraw(archiveManager.masterDraw(lastMoth), "省份/月份开卡数"));
		model.addAttribute(
				"draw2",
				handDraw(archiveManager.masterDraw(DateUtil.getDelayDate(1)),
						"省份/月份开卡数"));
		model.addAttribute(
				"draw3",
				handDraw(archiveManager.masterDraw(DateUtil.getDelayDate(1)),
						"省份/月份开卡数"));
		return "home/master";
	}

	public Map<String, String> handDraw(List<Map<String, Object>> draw,
			String title) {
		Map<Object, Object> drawMap = new HashMap<Object, Object>();
		for (Map<String, Object> m1 : draw) {
			drawMap.put(m1.get("province"), m1.get("order_num"));
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("xAxis", StringUtils.join(drawMap.keySet(), ","));
		map.put("series", StringUtils.join(drawMap.values(), ","));
		map.put("text", title);
		System.out.println(JSONObject.toJSONString(map));
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
