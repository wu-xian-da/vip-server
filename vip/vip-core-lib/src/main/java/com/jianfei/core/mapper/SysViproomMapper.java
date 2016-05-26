package com.jianfei.core.mapper;


import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.persistence.MyBatisDao;
import com.jianfei.core.bean.SysAirport;
@MyBatisDao
public interface SysViproomMapper {
	//分页显示vip室信息
	List<SysViproom> page(Map<String,Object> params);
	
	//逻辑删除vip室信息
	int deleteByVipRommId(String viproomId);
	
	//添加vip室信息
	int insert(SysViproom record);

    int insertSelective(SysViproom record);
    
    //根据id号返回vip室信息
    SysViproom selectByPrimaryKey(String viproomId);

    int updateByPrimaryKeySelective(SysViproom record);

    int updateByPrimaryKeyWithBLOBs(SysViproom record);
    
    //更新vip室信息
    int updateByPrimaryKey(SysViproom record);

    List<SysViproom> getVipRoomList(SysAirport airport);
}