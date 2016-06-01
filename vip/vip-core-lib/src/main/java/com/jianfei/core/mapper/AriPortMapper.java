/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午5:35:29
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.SysAirport;
import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月17日 下午5:35:29
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface AriPortMapper extends BaseMapper<AriPort> {

	public void deleteAriport(Long userId);

	void batchInsertUserAriport(List<Map<String, Object>> list);

	List<AriPort> selectAriportByUserId(Long id);

	/**
	 * 获取机场的省份列表
	 * 
	 * @return
	 */
	List<String> getAriPortProvince();

	/**
	 * 根据省份获取机场信息
	 * 
	 * @param province
	 * @return
	 */
	List<SysAirport> getAirPortByProvince(String province);

	/**
	 * @param 查找城市
	 * @return
	 */
	List<Map<String, Object>> selectCityById(Map<String, Object> map);

	/**
	 * mapList(机场的map集合)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> mapList(Map<String, Object> map);
}
