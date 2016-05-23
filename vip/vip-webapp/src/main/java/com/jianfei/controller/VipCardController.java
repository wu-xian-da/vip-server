/**
 * @椤圭洰鍚�vip
 * @鐗堟湰淇℃伅:1.0
 * @date:2016骞�鏈�2鏃�涓婂崍10:51:37
 * Copyright (c) 2016寤洪绉戣仈鍏徃-鐗堟潈鎵�湁
 *
 */
package com.jianfei.controller;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.SystemOutLogger;
import org.apache.shiro.authz.annotation.RequiresUser;
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
import com.jianfei.core.bean.File;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;

/**
 * vip鍗＄鐞�
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016骞�鏈�2鏃�涓婂崍10:51:37 
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
	 * 璺宠浆鍒皏ip鍗＄鐞嗛〉闈�
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
	 * 灞曠ずvip鍗″垪琛�
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
	public Grid showVipCardList(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
			HttpServletRequest request){
		System.out.println("");
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		if(searchParams !=null){
			if(searchParams.get("cardState") !=null && searchParams.get("cardState").equals(3)){
				searchParams.remove("cardState");
			}
		}
		PageInfo<AppVipcard> pageInfo = vipCardManagerImpl.showCardListPage(pageNo, pageSize, searchParams);
		intiWebContentEnv();
		return vipCardManagerImpl.bindVipCardGridData(pageInfo);
		
		
	}
	
	/**
	 * 閫昏緫鍒犻櫎vipcard
	 * delVipCard
	 * @param vipCard
	 * @return
	 * Map<String,Object>
	 * @version  1.0.0
	 */
	@ResponseBody
	@RequestMapping(value="delVipCard",method=RequestMethod.POST)
	public MessageDto<AppVipcard> delVipCard(AppVipcard vipCard){
		vipCardManagerImpl.deleteVipCardByCardNo(vipCard.getCardNo());
		return new MessageDto<AppVipcard>().setOk(true).setMsgBody(
				MessageDto.MsgFlag.SUCCESS);
	
	}
	
	/**
	 * 瀵煎叆excel琛ㄦ牸鏁版嵁
	 */
	@RequestMapping(value="importExcel",method=RequestMethod.POST)
	@ResponseBody
	public MessageDto<AppVipcard> importExcel(String filePath){
		vipCardManagerImpl.importExcelData(filePath);
		return new MessageDto<AppVipcard>().setOk(true).setMsgBody(
				MessageDto.MsgFlag.SUCCESS);
		
	}
	/**
	 * 灏嗘暟鎹〃涓殑鏁版嵁瀵煎叆鍒癳xcel涓�
	 */
	@RequestMapping(value="exportExcel")
	@ResponseBody
	public MessageDto<AppVipcard> exportExcel(String filePath,HttpServletResponse response){
		filePath = "f://";
		String fileName = filePath +"vipCard.xlsx";
		vipCardManagerImpl.exportDataToExcel(fileName);
		return new MessageDto<AppVipcard>().setOk(true).setMsgBody(
				MessageDto.MsgFlag.SUCCESS);
	}
	
	/**
	 * 鑾峰彇鐢ㄤ簬鎺掑簭鐨勫瓧娈�
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
