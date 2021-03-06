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
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.common.utils.PageDto;

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
    PageInfo<SysViproom> simplePage(int pageNo, int pageSize,Map<String, Object> params);
    
    //逻辑删除vip室信息
  	public void delVipRoom(String viproomId);
    
	//添加vip室信息
  	public int addVipRoom(SysViproom viproom);
	
	//编辑vip室信息
  	public void updateVipRoom(SysViproom viproom);
  	
  	//根据vip室id号返回vip室信息
  	public SysViproom selVipRoomById(String viproomId);

	/**
	 * 获取VIP室信息
	 * @return
     */
	SysViproom getVipRoomInfo(String vipRoomId);

	/**
	 * 分页获取Vip室信息
	 * @param pageDto 分页信息
	 * @param airport 查询相关条件
	 * @param coordinate 坐标信息
	 * @return
	 */
	PageInfo<SysViproom> pageVipRoom(PageDto pageDto, SysAirport airport, String coordinate);

	/**
	 * 分页获取Vip室信息
	 * @param pageDto 分页信息
	 * @param airport 查询相关条件
     * @return
     */
	PageInfo<SysViproom> pageVipRoom(PageDto pageDto, SysAirport airport);
	
	/**
	 * 启用vip室功能
	 * @param viproomId
	 * @return
	 */
    int startUsingByVipRommId(String viproomId);
    
    /**
     * 禁用vip室功能
     * @param viproomId
     * @return
     */
    int forbideenUsingByVipRommId(String viproomId);
}
