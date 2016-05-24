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

	/* (non-Javadoc)
	 * @see com.jianfei.core.service.base.VipRoomManager#pageVipRoom(com.jianfei.core.common.utils.PageDto, com.jianfei.core.bean.SysAirport, java.lang.String)
	 */
	@Override
	public PageInfo<SysViproom> pageVipRoom(PageDto pageDto, SysAirport airport, String coordinate) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jianfei.core.service.base.VipRoomManager#pageVipRoom(com.jianfei.core.common.utils.PageDto, com.jianfei.core.bean.SysAirport)
	 */
	@Override
	public PageInfo<SysViproom> pageVipRoom(PageDto pageDto, SysAirport airport) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
