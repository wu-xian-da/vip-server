/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午5:14:02
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.common.interImp;

import com.jianfei.common.mapper.OrderState;

/**
 * 申请退款
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月17日 下午5:14:02 
 * 
 * @version 1.0.0
 *
 */
public class ApplyRefunds implements OrderState{

	/* (non-Javadoc)
	 * @see com.jianfei.common.mapper.OrderState#handle(int)
	 */
	@Override
	public void handle(int operationType) {
		// TODO Auto-generated method stub
		System.out.println("将order状态变为 审核中.....");
	}

}
