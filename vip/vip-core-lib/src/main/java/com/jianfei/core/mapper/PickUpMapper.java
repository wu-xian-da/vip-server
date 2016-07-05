package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: 接送机映射类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月5日 下午2:59:02
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface PickUpMapper {

	/**
	 * mapList(根据条件查询接送机信息)
	 * 
	 * @param searchParams
	 * @return List<Map<String, Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> mapList(Map<String, Object> searchParams);

	/**
	 * updateState(更改接送机状态)
	 * 
	 * @param params
	 *            void
	 * @version 1.0.0
	 */
	void updateState(Map<String, Object> params);

}
