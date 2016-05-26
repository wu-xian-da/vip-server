/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-上午12:23:04
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.pay;

/**
 *
 * @Description: TODO
 * @author: liu.lei@jianfeitech.com 
 * @date: 2016年5月25日 上午12:23:04 
 * 
 * @version 1.0.0
 *
 */
public interface PreCreateParamBuilder<T,K> {
	public K buildCreateParam(T buildObj);
}
