/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午10:09:28
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

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.base.impl.VipRoomManagerImp;

/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月18日 上午10:09:28 
 * 
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("viproom")
public class VipRoomController extends BaseController {
	@Autowired
	private VipRoomManagerImp vipRoomManagerImp;
	
	@RequestMapping("/gotoVipRoomView")
	public String test(){
		return "viproom/vipRoomManagement";
	}
	
	
	/**
	 * 分页显示vip室信息
	 * showVipRoomList
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 *Grid
	 * @version  1.0.0
	 */
	@RequestMapping("showVipRoomList")
	@ResponseBody
	public Grid showVipRoomList(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
			HttpServletRequest request){
		//查询条件
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(
				request, "_");
		PageInfo<SysViproom> page =  vipRoomManagerImp.simplePage(pageNo, pageSize, searchParams);
		intiWebContentEnv();
		return vipRoomManagerImp.bindVipRoomGridData(page);
	}
	
	/**
	 * 逻辑删除vip室信息
	 * delVipRoomById
	 * @param viproom
	 * @return
	 * MessageDto<AppVipcard>
	 * @version  1.0.0
	 */
	@RequestMapping(value="delVipRoomById",method=RequestMethod.POST)
	@ResponseBody
	public  MessageDto<AppVipcard> delVipRoomById(SysViproom viproom){
		
		vipRoomManagerImp.delVipRoom(viproom.getViproomId());
		return new MessageDto<AppVipcard>().setOk(true).setMsgBody(
				MessageDto.MsgFlag.SUCCESS);
	}
}
