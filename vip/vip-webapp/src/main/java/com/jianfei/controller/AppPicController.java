/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午11:43:33
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.ArrayList;
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
import com.google.common.collect.Lists;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.base.AppPictureService;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月23日 下午11:43:33
 * 
 * @version 1.0.0
 *
 */

@Controller
@RequestMapping(value = "/app")
public class AppPicController extends BaseController {

	@Autowired
	private AppPictureService appPictureService;

	@RequestMapping(value = "home")
	public String home() {

		return "app/app";
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
		PageHelper.startPage(pageNo, pageSize);
		MessageDto<List<AppPicture>> messageDto = appPictureService
				.get(searchParams);
		PageInfo<AppPicture> pageInfo = new PageInfo<AppPicture>(
				messageDto.getData());
		return bindUserGridData(pageInfo);
	}

	@RequestMapping(value = "form")
	public String form(Integer id, Model model) {
		if (null != id && 0 != id) {
			MessageDto<AppPicture> messageDto = appPictureService
					.selectByPrimaryKey(id);
			if (messageDto.isOk())
				model.addAttribute("appPicture", messageDto.getData());
		}
		return "app/picForm";
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
	public MessageDto<String> save(AppPicture appPicture) {
		appPicture.setDtflag(GloabConfig.OPEN);
		return appPictureService.save(appPicture);

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
	public MessageDto<String> delete(Integer id) {
		return appPictureService.deleteByPrimaryKey(id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Grid bindUserGridData(PageInfo<AppPicture> pageInfo) {
		List<AppPicture> list = pageInfo.getList();
		if (CollectionUtils.isEmpty(list)) {
			list = Lists.newArrayList();
		}
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (AppPicture user : list) {
			Map<String, Object> map = MapUtils.<AppPicture> entityInitMap(user);
			maps.add(map);
		}
		Grid grid = new Grid();
		grid.setRows(maps);
		grid.setTotal(pageInfo.getTotal());
		return grid;
	}
}
