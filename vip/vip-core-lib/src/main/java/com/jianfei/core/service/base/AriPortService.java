/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午6:49:09
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.common.persistence.BaseService;
import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description: 机场业务层
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月18日 上午6:49:09
 * 
 * @version 1.0.0
 * @param <T>
 *
 */

public interface AriPortService<T extends Serializable> extends BaseService<T> {

	MessageDto<String> batchInsertUserAriport(String id,String name,String loginName,String ids);

	MessageDto<List<AriPort>> selectAriportByUserId(Long id);

	List<Map<String, Object>> datePermissionData(Long id);

	/**
	 * 获取机场的省份列表
	 * @return
     */
	List<String> getAriPortProvince();

	/**
	 *  根据省份获取机场信息
	 * @param provinceId
	 * @return
     */
	List<SysAirport> getAirPortByProvince(String provinceId);
}
