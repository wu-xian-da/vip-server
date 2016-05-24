/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月19日-下午5:07:45
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.mapper.AriPortMapper;
import com.jianfei.core.mapper.SysViproomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.dto.OrderShowInfoDto;

/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月19日 下午5:07:45 
 * 
 * @version 1.0.0
 *
 */
@Service
public class VipRoomManager implements com.jianfei.core.service.base.VipRoomManager {


	@Override
	public PageInfo<OrderShowInfoDto> simplePage(int pageNo, int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
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

	/* (non-Javadoc)
	 * @see com.jianfei.core.service.base.VipRoomManager#delVipRoom()
	 */
	@Override
	public void delVipRoom() {
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
		List<SysViproom> list = viproomMapper.getVipRoomList(airport);
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

	@Autowired
	private SysViproomMapper viproomMapper;
}
