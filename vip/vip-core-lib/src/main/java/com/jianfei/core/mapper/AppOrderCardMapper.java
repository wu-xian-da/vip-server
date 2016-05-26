/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月20日-下午2:52:10
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import com.jianfei.core.bean.AppOrderCard;
import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月20日 下午2:52:10 
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface AppOrderCardMapper {
	//返回vip卡号和初始金额
	AppOrderCard getAppOrderCard(String orderId);
}
