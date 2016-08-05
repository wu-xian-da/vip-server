package com.jianfei.core.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppConfig;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.enu.PictureType;
import com.jianfei.core.common.enu.RightType;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.VipCardInfoDto;
import com.jianfei.core.mapper.AppConfigMapper;
import com.jianfei.core.service.base.AppConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/6 18:26
 */
@Service
public class AppConfigManagerImpl implements AppConfigManager {
   @Autowired
   private AppPictureManagerImpl appPictureManager;
   @Autowired
   private  AppConfigMapper appConfigMapper;
    /**
     * 获取配置信息VIP权益
     *
     * @return
     */
    @Override
    public VipCardInfoDto getVipCardInfo(String phone) {
        List<AppPicture> pictureList;
		AppConfig appConfig;
		if (StringUtils.isBlank(phone)){
			appConfig=getAppConfig(RightType.BEFORE_LOGIN_RIGHT);
			pictureList=appPictureManager.getPicture(PictureType.BEFORE_VIP_APP_RIGHT);
		}else {
			appConfig=getAppConfig(RightType.AFTER_LOGIN_RIGHT);
			pictureList=appPictureManager.getPicture(PictureType.AFTER_VIP_APP_RIGHT);
		}
		VipCardInfoDto vipCardInfoDto=new VipCardInfoDto();
		vipCardInfoDto.setRight(appConfig.getContent());
        vipCardInfoDto.setImages(pictureList);
        return vipCardInfoDto;
    }

    /**
     * 获取常见问题
     *
     * @return
     */
    @Override
    public AppConfig getAppConfig(RightType rightType) {
        List<AppConfig> list=appConfigMapper.selectByType(rightType.getName());
        return list == null || list.isEmpty() ? new AppConfig() : list.get(0);
    }
    
	/**
	 * 分页获取权益信息
	 */
	@Override
	public PageInfo<AppConfig> page(int pageNo, int pageSize, Map<String, Object> map) {
		// 显示第几页
		PageHelper.startPage(pageNo, pageSize);
		// 查询条件
		List<AppConfig> list = appConfigMapper.page(map);
		PageInfo<AppConfig> page = new PageInfo(list);
		return page;
	}
	
	
	/**
	 * 将权益信息转换成Grid格式 bindAppConfigGridData
	 * @param pageInfo
	 * @return
	 */
	public Grid bindAppConfigGridData(PageInfo<AppConfig> pageInfo) {
		List<AppConfig> list = pageInfo.getList();
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (AppConfig appConfig : list) {
			Map<String, Object> map = MapUtils.<AppConfig> entityInitMap(appConfig);
			maps.add(map);
		}
		Grid grid = new Grid();
		grid.setRows(maps);
		grid.setTotal(pageInfo.getTotal());
		return grid;
	}
	
	/**
	 * 添加权益
	 */
	@Override
	public int addRight(AppConfig appConfig) {
		// TODO Auto-generated method stub
		return appConfigMapper.insert(appConfig);
	}
	
	/**
	 * 逻辑删除权益
	 */
	@Override
	public int delRight(String id) {
		// TODO Auto-generated method stub
		return appConfigMapper.delRight(id);
	}
	
	/**
	 * 根据id获取权益信息
	 */
	@Override
	public AppConfig selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return appConfigMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 更新权益信息
	 */
	@Override
	public int updateByPrimaryKeySelective(AppConfig appConfig) {
		// TODO Auto-generated method stub
		return appConfigMapper.updateByPrimaryKeySelective(appConfig);
	}
}
