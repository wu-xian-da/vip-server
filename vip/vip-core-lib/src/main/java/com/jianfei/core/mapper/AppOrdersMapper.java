package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.common.persistence.MyBatisDao;
import com.jianfei.core.dto.OrderShowInfoDto;

@MyBatisDao
public interface AppOrdersMapper{
	//分页查询
	List<OrderShowInfoDto> get(Map<String, Object> params);
	
	//更新订单状态
	int updateOrderState(Map<String,Object> params);
	
	
	
	int deleteByPrimaryKey(String orderId);

    int insert(AppOrders record);

    int insertSelective(AppOrders record);

    AppOrders selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(AppOrders record);

    int updateByPrimaryKey(AppOrders record);
}