/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月19日-下午5:07:45
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.mapper.SysViproomMapper;
import com.jianfei.core.service.base.VipRoomManager;

/**
 * vip室
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月19日 下午5:07:45 
 * 
 * @version 1.0.0
 *
 */
@Service
public class VipRoomManagerImpl implements VipRoomManager {
	@Autowired
	private SysViproomMapper sysViproomMapper;
	/**
	 * 分页查询
	 */
	@Override
	public PageInfo<SysViproom> simplePage(int pageNo, int pageSize, Map<String, Object> params) {
		// 显示第几页
		PageHelper.startPage(pageNo, pageSize);
		// 查询条件
		List<SysViproom> list = sysViproomMapper.page(params);
		PageInfo<SysViproom> page = new PageInfo(list);
		return page;
	}
	
	/**
	 * 逻辑删除vip室信息
	 */
	@Override
	public void delVipRoom(String viproomId) {
		sysViproomMapper.deleteByVipRommId(viproomId);
	}
	
	/**
	 * 将vip室信息转换成Grid格式 bindVipCardGridData
	 * 
	 * @param pageInfo
	 * @return Grid
	 * @version 1.0.0
	 */
	public Grid bindVipRoomGridData(PageInfo<SysViproom> pageInfo) {
		List<SysViproom> list = pageInfo.getList();
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (SysViproom viproom : list) {
			Map<String, Object> map = MapUtils.<SysViproom> entityInitMap(viproom);
			maps.add(map);
		}
		Grid grid = new Grid();
		grid.setRows(maps);
		grid.setTotal(pageInfo.getTotal());
		return grid;
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.jianfei.core.service.base.VipRoomManager#addVipRoom()
	 */
	@Override
	public void addVipRoom() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.jianfei.core.service.base.VipRoomManager#updateVipRoom()
	 */
	@Override
	public void updateVipRoom() {
		// TODO Auto-generated method stub

	}

	/**
	 * 分页获取Vip室信息
	 *
	 * @param pageDto 分页信息
	 * @param airport 查询相关条件
	 * @return
	 */
	@Override
	public PageInfo<SysViproom> pageVipRoom(PageDto pageDto, SysAirport airport) {
		PageHelper.startPage(pageDto.getPageNo(), pageDto.getPageSize());
		List<SysViproom> list = sysViproomMapper.getVipRoomList(airport);
		PageInfo<SysViproom> pageInfo = new PageInfo(list);
		return pageInfo;
	}

	/**
	 * 分页获取Vip室信息
	 *
	 * @param pageDto    分页信息
	 * @param airport    查询相关条件
	 * @param coordinate 坐标信息
	 * @return
	 */
	@Override
	public PageInfo<SysViproom> pageVipRoom(PageDto pageDto, SysAirport airport, String coordinate) {
		//1、机场ID 机场名称 机场省份是否存在 如果不存在则使用坐标
		if(StringUtils.isNotBlank(airport.getAirportId()) ||StringUtils.isNotBlank(airport.getAirportName())
				||StringUtils.isNotBlank(airport.getProvince())){
			return pageVipRoom(pageDto,airport);
		}
		//2、判断坐标是否存在
		if (StringUtils.isNotBlank(coordinate)){
			//TODO 根据经纬度坐标获取省市信息
			airport.setProvince("");
			airport.setCity("");
		}
		//3、查询数据
		return pageVipRoom(pageDto,airport);
	}


}
