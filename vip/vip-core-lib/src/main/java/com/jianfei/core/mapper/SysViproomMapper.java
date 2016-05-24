package com.jianfei.core.mapper;

<<<<<<< HEAD
import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.persistence.MyBatisDao;
=======
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.common.persistence.MyBatisDao;
import com.sun.tools.javac.util.List;
>>>>>>> c36f3a432751506b266d46a2efd62fa5b30a95cf

@MyBatisDao
public interface SysViproomMapper {
	//分页显示vip室信息
	List<SysViproom> page(Map<String,Object> params);
	
	//逻辑删除vip室信息
	int deleteByVipRommId(String viproomId);

    int insert(SysViproom record);

    int insertSelective(SysViproom record);

    SysViproom selectByPrimaryKey(String viproomId);

    int updateByPrimaryKeySelective(SysViproom record);

    int updateByPrimaryKeyWithBLOBs(SysViproom record);

    int updateByPrimaryKey(SysViproom record);

    List<SysViproom> getVipRoomList(SysAirport airport);
}