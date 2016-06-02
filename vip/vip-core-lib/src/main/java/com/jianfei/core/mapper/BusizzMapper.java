/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午3:45:44
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.User;
import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: 业务员
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月23日 下午3:45:44
 * 
 * @version 1.0.0
 *
 */
@MyBatisDao
public interface BusizzMapper extends BaseMapper<User> {
	User getUserByName(String loginName);

	/**
	 * list(获取业务员信息)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> listMap(Map<String, Object> map);

	/**
	 * initpwd(初始密码)
	 * 
	 * @param map
	 *            void
	 * @version 1.0.0
	 */
	void initpwd(Map<String, Object> map);

	/**
	 * selectMap(更具状态和入职日期查询员工)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> selectMap(Map<String, Object> map);

}
