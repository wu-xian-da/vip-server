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
import javax.servlet.http.HttpServletResponse;

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
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.enu.VipOrderState;
import com.jianfei.core.common.utils.ExportAip;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.base.AppCustomerManager;
import com.jianfei.core.service.base.AppPictureManager;

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
		MessageDto<List<AppCustomer>> messageDto = appCustomerManager
				.get(searchParams);
		if (messageDto.isOk()) {
			return bindGridData(new PageInfo<AppCustomer>(messageDto.getData()));
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

	@RequestMapping(value = "/download")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		MessageDto<List<AppCustomer>> messageDto = appCustomerManager
				.get(new MapUtils.Builder().build());
		if (messageDto.isOk()) {
			List<ExportAip> dataset = new ArrayList<ExportAip>();
			for (AppCustomer appCustomer : messageDto.getData()) {
				ExportAip exportAip = new ExportAip(
						StringUtils.obj2String(appCustomer.getCustomerName()),
						StringUtils.obj2String(appCustomer.getPhone()),
						StringUtils.obj2String(appCustomer.getCreateTime()),
						StringUtils.obj2String(appCustomer.getAddress()),
						StringUtils.obj2String(appCustomer.getEmail()),
						returnPayState(StringUtils.obj2String(appCustomer
								.getOrderStatu())));
				dataset.add(exportAip);
			}
			download(response, new String[] { "姓名", "手机号", "日期", "常住地址", "邮箱",
					"用户状态" }, dataset, "vip用户.xls");
		}
	}

	/**
	 * 支付状态
	 * 
	 * @param state
	 * @return
	 */
	public String returnPayState(String state) {
		System.out.println(state);
		if (String.valueOf(VipOrderState.NOT_PAY.getName()).equals(state)) {
			return "未支付";
		} else if (String.valueOf(VipOrderState.ALREADY_PAY.getName()).equals(
				state)) {
			return "已支付";
		} else if (String.valueOf(VipOrderState.ALREADY_REFUND.getName())
				.equals(state)) {
			return "已退款";
		} else if (String.valueOf(VipOrderState.AUDIT_PASS.getName()).equals(
				state)) {
			return "审核通过";
		} else if (String.valueOf(VipOrderState.BEING_AUDITED.getName())
				.equals(state)) {
			return "正在审核";
		}
		return "";
	}
}
