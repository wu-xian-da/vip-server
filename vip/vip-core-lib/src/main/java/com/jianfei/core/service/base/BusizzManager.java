/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午3:41:57
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description: 业务员
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月23日 下午3:41:57
 * 
 * @version 1.0.0
 *
 */
public interface BusizzManager<T> {

	MessageDto<String> saveUser(User user, String arids, String roleids);

	MessageDto<String> delete(Long id);

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
	MessageDto<String> initpwd(Map<String, Object> map);

	/**
	 * save(保存机场信息)
	 * 
	 * @param t
	 * @return MessageDto<T>
	 * @version 1.0.0
	 */
	MessageDto<T> save(T t);

	/**
	 * update(更新机场信息)
	 * 
	 * @param t
	 * @return MessageDto<T>
	 * @version 1.0.0
	 */
	MessageDto<T> update(T t);

	/**
	 * selectMap(更具状态和入职日期查询员工)
	 * 
	 * @param map
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> selectMap(Map<String, Object> map);
}
