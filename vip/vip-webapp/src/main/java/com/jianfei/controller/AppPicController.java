/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午11:43:33
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.utils.ExportAip;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.ObjectUtils;
import com.jianfei.core.service.base.AppCustomerManager;
import com.jianfei.core.service.base.AppPictureManager;

/**
 *
 * @Description: 图片管理
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
	private AppPictureManager appPictureManager;
	@Autowired
	private AppCustomerManager appCustomerManager;

	@RequestMapping(value = "home")
	public String home() {

		return "app/app";
	}

	@RequestMapping(value = "home/vip")
	public String homeVip() {

		return "app/vip";
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
		PageHelper.startPage(page, rows);
		MessageDto<List<AppPicture>> messageDto = appPictureManager
				.get(searchParams);
		if (messageDto.isOk()) {
			return bindGridData(new PageInfo<AppPicture>(messageDto.getData()));
		}
		return bindGridData(new PageInfo<AppPicture>());
	}

	@RequestMapping(value = "form")
	public String form(Integer id, Model model) {
		if (null != id && 0 != id) {
			MessageDto<AppPicture> messageDto = appPictureManager
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
		appPicture.setUpdatetime(new Date());
		if (ObjectUtils.isEmpty(appPicture.getPriority())) {
			appPicture.setPriority(0);
		}
		appPicture.setDtflag(GloabConfig.OPEN);
		if (null != appPicture.getPictureId() && 0 != appPicture.getPictureId()) {
			return appPictureManager.updateByPrimaryKeySelective(appPicture);
		}
		return appPictureManager.save(appPicture);

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
		return appPictureManager.deleteByPrimaryKey(id);
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
	@RequestMapping(value = "/list/vip", method = RequestMethod.POST)
	@ResponseBody
	public Grid vipList(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows,
			HttpServletRequest request) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		PageHelper.startPage(page, rows);
		MessageDto<List<Map<String, Object>>> messageDto = appCustomerManager
				.get(searchParams);
		if (messageDto.isOk()) {
			return bindGridData(new PageInfo<Map<String, Object>>(
					messageDto.getData()));
		}
		return bindGridData(new PageInfo<AppCustomer>());
	}

	@RequestMapping(value = "/look")
	public String lookVipInfo(String id, Model model) {
		MessageDto<AppCustomer> messageDto = appCustomerManager
				.selectByPrimaryKey(id);
		if (messageDto.isOk()) {
			model.addAttribute("customer", messageDto.getData());
		}
		appCustomerManager.batchDealMsg(id, model);
		return "app/vipInfo";
	}

	@ResponseBody
	@RequestMapping(value = "/updateDeliveryState/{id}")
	public MessageDto<String> updateDeliveryState(
			@PathVariable(value = "id") String id) {
		return appCustomerManager.updateDeliveryState(id);

	}

	@RequestMapping(value = "/download")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		MessageDto<List<Map<String, Object>>> messageDto = appCustomerManager
				.get(searchParams);
		if (messageDto.isOk()) {
			List<ExportAip> dataset = new ArrayList<ExportAip>();
			for (Map<String, Object> map : messageDto.getData()) {
				ExportAip exportAip = new ExportAip(map.get("customer_name"),
						map.get("customer_phone"), map.get("sex"),
						map.get("card_type"), map.get("customer_identi"), map.get("birthday"),
						map.get("order_id"), map.get("insured"),
						map.get("orderstate"));
				dataset.add(exportAip);
			}
			download(response, new String[] { "姓名", "手机号", "性别", "证件类型",
					"证件号码", "出生日期", "订单号", "投保状态", "用户状态" }, dataset,
					"vip用户.xls");
		}
	}
}
