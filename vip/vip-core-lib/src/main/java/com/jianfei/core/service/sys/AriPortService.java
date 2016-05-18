/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午6:49:09
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.mapper.AriPortMapper;

/**
 *
 * @Description: 机场业务层
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月18日 上午6:49:09
 * 
 * @version 1.0.0
 *
 */

@Service
@Transactional
public class AriPortService {

	@Autowired
	private AriPortMapper ariPortMapper;

	/**
	 * ariPortMapper
	 *
	 * @return the ariPortMapper
	 * @version 1.0.0
	 */

	public AriPortMapper getAriPortMapper() {
		return ariPortMapper;
	}

	/**
	 * @param ariPortMapper the ariPortMapper to set
	 */
	public void setAriPortMapper(AriPortMapper ariPortMapper) {
		this.ariPortMapper = ariPortMapper;
	}
}
