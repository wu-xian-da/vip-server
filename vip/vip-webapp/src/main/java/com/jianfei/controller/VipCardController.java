/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月22日-上午10:51:37
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;

/**
 * vip卡管理
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月22日 上午10:51:37 
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("vipCard")
public class VipCardController extends BaseController {
	@Autowired
	private VipCardManagerImpl vipCardManagerImpl;
	/**
	 * 跳转到vip卡管理页面
	 * goVipCardManageView
	 * @return
	 * String
	 * @version  1.0.0
	 */
	@RequestMapping(value="goVipCardManageView")
	public String goVipCardManageView(){
		return "vipcard/vipCardManagement";
	}
	
	/**
	 * 展示vip卡列表
	 * showVipCardList
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 * Grid
	 * @version  1.0.0
	 */
	@RequestMapping(value="showVipCardList",method = RequestMethod.POST)
	@ResponseBody
	public Grid showVipCardList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			HttpServletRequest request){
		
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		searchParams.put("sort", sortCplumn(request));
		searchParams.put("order", request.getParameter("order"));
		PageInfo<AppVipcard> pageInfo = vipCardManagerImpl.showCardListPage(pageNo, pageSize, searchParams);
		intiWebContentEnv();
		return vipCardManagerImpl.bindVipCardGridData(pageInfo);
		
		
	}
	
	/**
	 * 获取用于排序的字段
	 * 
	 * sortCplumn
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
}
