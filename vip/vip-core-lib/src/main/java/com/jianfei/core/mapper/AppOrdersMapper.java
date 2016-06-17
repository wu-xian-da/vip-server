package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.common.persistence.MyBatisDao;
import com.jianfei.core.dto.OrderDetailInfo;
import com.jianfei.core.dto.OrderShowInfoDto;

@MyBatisDao
public interface AppOrdersMapper {
	// 订单列表分页查询
	List<OrderShowInfoDto> get(Map<String, Object> params);

	// 退单列表分页查询
	List<OrderShowInfoDto> page(Map<String, Object> params);

	// 更新订单状态
	int updateOrderState(Map<String, Object> params);

	// 根据订单编号返回订单详细信息
	OrderDetailInfo selOrderDetailInfo(String orderId);

	int deleteByPrimaryKey(String orderId);

	int insert(AppOrders record);

	int insertSelective(AppOrders record);

	AppOrders selectByPrimaryKey(String orderId);

	int updateByPrimaryKeySelective(AppOrders record);

	int updateByPrimaryKey(AppOrders record);

	/**
	 * selectOrder(订单状态查询)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> selectOrder(Map<String, Object> map);
	
	/**
	 * 支付宝回调通知
	 * 参数类型见：com.jianfei.core.common.pay.PayNotifyRequest
	 * @param params
	 * @return
	 */
	int payNotify(Map<String, Object> params);
	
	/**
	 * 根据订单号返回订单基本信息
	 */
	AppOrders getOrderInfoByOrderId(String orderId);
	
	/**
	 * 查询需要开发票的订单信息
	 */
	List<OrderShowInfoDto> invoicePageList(Map<String,Object> map);
}