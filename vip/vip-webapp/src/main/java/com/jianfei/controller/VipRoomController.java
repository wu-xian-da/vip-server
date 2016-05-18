/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午10:09:28
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class VipRoomController {
	
	@RequestMapping("/gotoVipRoomView")
	public String test(){
		return "viproom/vipRoomManagement";
	}
}
