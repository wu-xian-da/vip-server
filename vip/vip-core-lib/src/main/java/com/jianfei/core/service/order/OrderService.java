/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-下午4:02:09
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.order;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.sun.tools.javac.util.List;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com 
 * @date: 2016年5月18日 下午4:02:09 
 * 
 * @version 1.0.0
 *
 */
public class OrderService {
	@Autowired
	private AppOrdersMapper appOrdersMapper;
	/**
	 * queryPage(分页查询)
	 *
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 *            封装查询参数
	 * @return Page<Role>
	 * @version 1.0.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<AppOrders> simplePage(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<AppOrders> list = (List<AppOrders>) appOrdersMapper.get(params);
		PageInfo<AppOrders> pageInfo = new PageInfo(list);
		return pageInfo;
	}
}
