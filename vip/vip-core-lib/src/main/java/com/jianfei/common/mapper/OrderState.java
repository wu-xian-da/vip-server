/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午4:55:12
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.common.mapper;

/**
 *	状态机模式
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月17日 下午4:55:12 
 * 
 * @version 1.0.0
 *
 */
public interface OrderState {
	void handle(int operationType);
}
