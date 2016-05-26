package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.persistence.MyBatisDao;

@MyBatisDao
public interface AppCustomerMapper {
	int deleteByPrimaryKey(String customerId);

	AppCustomer selectByPrimaryKey(String customerId);

	AppCustomer selectByPhone(String phone);

	List<AppCustomer> get(Map<String, Object> map);

	/**
	 * orderInfo(用户订单信息)
	 * 
	 * @param id
	 *            vip用户ID
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> orderInfo(String id);

	/**
	 * vipCardInfo(vip卡信息)
	 * 
	 * @param id
	 *            vip用户ID
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> vipCardInfo(String id);

	/**
	 * postInfo(发票信息)
	 * 
	 * @param id
	 *            vip用户ID
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> postInfo(String id);

	/**
	 * backMoneyInfo(退款信息)
	 * 
	 * @param id
	 *            vip用户ID
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> backMoneyInfo(String id);

	/**
	 * vipCardRescordInfo(卡的消费记入)
	 * 
	 * @param id
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> vipCardRescordInfo(String id);
}