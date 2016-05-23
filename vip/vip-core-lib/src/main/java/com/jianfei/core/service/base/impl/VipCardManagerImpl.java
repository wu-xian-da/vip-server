package com.jianfei.core.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.mapper.AppVipcardMapper;
import com.jianfei.core.service.base.VipCardManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Vip卡管理
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 10:45
 */
@Service
public class VipCardManagerImpl implements VipCardManager {
	@Autowired
	private AppVipcardMapper appVipcardMapper;

    /**
     * 添加VipCard
     *
     * @param vipcard
     * @return
     */
    @Override
    public boolean addVipCard(AppVipcard vipcard) {
        return false;
    }

    /**
     * 更新VipCard
     *
     * @param vipcard
     * @return
     */
    @Override
    public boolean updateVipCard(AppVipcard vipcard) {
        return false;
    }

    /**
     * 添加VipCard使用记录
     *
     * @param customer
     * @return
     */
    @Override
    public boolean addVipUseInfo(AppCustomer customer) {
        return false;
    }

    /**
     * 分页获取用户使用记录
     *
     * @param pageNo    页数
     * @param pageSize  每页大小
     * @param vipCardNo Vip卡号
     * @return
     */
    @Override
    public PageInfo<AppCustomer> pageVipUseInfo(int pageNo, int pageSize, String vipCardNo) {
        return null;
    }

    /**
     * 根据vip卡No获取所有使用记录
     *
     * @param vipCardNo vipCardNo
     * @return List<AppCustomer>
     */
    @Override
    public List<AppCustomer> listAllVipUse(String vipCardNo) {
        return null;
    }
    
    /**
     * 分页显示vip卡列表信息
     */
	@Override
	public PageInfo<AppVipcard> showCardListPage(int pageNo, int pageSize, Map<String, Object> params) {
		 //显示第几页
		 PageHelper.startPage(pageNo, pageSize);
		 //查询条件
	     List<AppVipcard> list = appVipcardMapper.pageList(params);
	     PageInfo<AppVipcard> pageInfo = new PageInfo(list);
	     return pageInfo;
	}
	
	/**
	 * 将vip卡列表信息转换成Grid格式
	 * bindVipCardGridData
	 * @param pageInfo
	 * @return
	 * Grid
	 * @version  1.0.0
	 */
	public Grid bindVipCardGridData(PageInfo<AppVipcard> pageInfo) {
		List<AppVipcard> list = pageInfo.getList();
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (AppVipcard vipcard : list) {
			Map<String, Object> map = MapUtils.<AppVipcard> entityInitMap(vipcard);
			maps.add(map);
		}
		Grid grid = new Grid();
		grid.setRows(maps);
		grid.setTotal(pageInfo.getTotal());
		return grid;
	}
}
