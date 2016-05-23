package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.persistence.MyBatisDao;

@MyBatisDao
public interface AppVipcardMapper {
	//分页查询
	List<AppVipcard> pageList(Map<String,Object> params);
	
    int deleteByPrimaryKey(String cardNo);

    int insert(AppVipcard record);

    int insertSelective(AppVipcard record);

    AppVipcard selectByPrimaryKey(String cardNo);

    int updateByPrimaryKeySelective(AppVipcard record);

    int updateByPrimaryKey(AppVipcard record);
    
}