/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午2:33:50
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.common.mapper;


import com.jianfei.common.entity.Order;
import com.jianfei.core.common.persistence.MyBatisDao;
import com.jianfei.core.mapper.BaseMapper;

/**
 * 订单管理
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月17日 下午2:33:50 
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface OrderMapper extends BaseMapper<Order>{
	//根据订单编号返回订单信息
	Order getOrderByOrderId(String orderId);
}
