package com.jianfei.core.service.base;

/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月19日-下午4:49:40
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */


import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.dto.OrderShowInfoDto;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com 
 * @date: 2016年5月19日 下午4:49:40 
 * 
 * @version 1.0.0
 *
 */
public interface VipRoomManager {
	//分页查询vip室信息
    PageInfo<OrderShowInfoDto> simplePage(int pageNo, int pageSize,Map<String, Object> params);
    
	//添加vip室信息
	public void addVipRoom();
	
	//编辑vip室信息
	public void updateVipRoom();
	
	//删除vip室信息
	public void delVipRoom();
	
	
}
