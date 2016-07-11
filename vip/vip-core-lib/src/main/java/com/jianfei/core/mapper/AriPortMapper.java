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
import com.jianfei.core.dto.BaseDto;
import org.apache.ibatis.annotations.Param;

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

	/**
	 * 逻辑删除|开启场站
	 * 
	 * @param map
	 *            参数， key>>> id 主键，dtflag: 0:开启，1：禁用 void
	 * @version 1.0.0
	 */
	void forbitAirport(Map<String, Object> map);

	/**
	 * 删除指定用户关联的场站
	 * 
	 * @param userId
	 *            void
	 * @version 1.0.0
	 */
	public void deleteAriport(Long userId);

	void batchInsertUserAriport(List<Map<String, Object>> list);

	List<AriPort> selectAriportByUserId(Long id);

	/**
	 * 获取机场的省份列表
	 * 
	 * @return
	 */
	List<BaseDto> getAriPortProvince();

	/**
	 * 根据省份获取机场信息
	 * 
	 * @param province
	 * @return
	 */
	List<SysAirport> getAirPortByProvince(@Param("province") String province);

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

	AriPort selectAirPortInfoById(String airPortId);

	/**
	 * 验证机场是否存在
	 * 
	 * @return
	 */
	List<Map<String, Object>> validateAirPortExist(Map<String, Object> map);
}
