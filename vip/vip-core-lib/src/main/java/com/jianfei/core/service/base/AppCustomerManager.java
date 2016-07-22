/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月24日-上午11:26:22
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月24日 上午11:26:22
 * 
 * @version 1.0.0
 *
 */
public interface AppCustomerManager {

	/**
	 * get(查询VIP用户信息)
	 * 
	 * @param map
	 * @return MessageDto<List<AppCustomer>>
	 * @version 1.0.0
	 */
	MessageDto<List<Map<String, Object>>> get(Map<String, Object> map);

	/**
	 * delete(删除用户信息)
	 * 
	 * @param id
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> delete(String id);

	/**
	 * selectByPrimaryKey(查找指定的VIP用户)
	 * 
	 * @param id
	 * @return MessageDto<AppCustomer>
	 * @version 1.0.0
	 */
	MessageDto<AppCustomer> selectByPrimaryKey(String id);

	/**
	 * updateDeliveryState(更新投递状态未已投递)
	 * 
	 * @param id
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> updateDeliveryState(String id);

}
