package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.common.persistence.MyBatisDao;
import com.jianfei.core.dto.OrderDetailInfo;
import com.jianfei.core.dto.OrderShowInfoDto;

@MyBatisDao
public interface AppOrdersMapper{
	//订单列表分页查询
	List<OrderShowInfoDto> get(Map<String, Object> params);
	
	//退单列表分页查询
	List<OrderShowInfoDto> page(Map<String, Object> params);
	
	//更新订单状态
	int updateOrderState(Map<String,Object> params);
	
	//根据订单编号返回订单详细信息
	OrderDetailInfo selOrderDetailInfo(String orderId);
	
	
	int deleteByPrimaryKey(String orderId);

    int insert(AppOrders record);

    int insertSelective(AppOrders record);

    AppOrders selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(AppOrders record);

    int updateByPrimaryKey(AppOrders record);
}