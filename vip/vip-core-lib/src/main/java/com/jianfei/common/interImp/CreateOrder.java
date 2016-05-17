/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午4:57:10
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.common.interImp;

import com.jianfei.common.mapper.OrderState;

/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月17日 下午4:57:10 
 * 
 * @version 1.0.0
 *
 */
public class CreateOrder implements OrderState{

	/* (non-Javadoc)
	 * @see com.jianfei.common.mapper.OrderState#handle(int)
	 */
	@Override
	public void handle(int operationType) {
		// TODO Auto-generated method stub
		System.out.println("创建订单..");
	}

}
