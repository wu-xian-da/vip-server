/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午1:10:24
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.sys;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description: 用户管理
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午1:10:24
 * 
 * @version 1.0.0
 *
 */
public interface UserManaer<T extends Serializable> {

	/**
	 * delete(禁用用户)
	 * 
	 * @param id
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> delete(Long id);

	/**
	 * saveUser(保存用户)
	 * 
	 * @param user
	 * @param arids
	 * @param roleids
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> saveUser(User user, String arids, String roleids);

	/**
	 * get(查找用户信息)
	 * 
	 * @param params
	 * @return MessageDto<List<User>>
	 * @version 1.0.0
	 */
	MessageDto<List<User>> get(Map<String, Object> params);

	/**
	 * batchUpdateUserRoles(为用户批量授予权限)
	 * 
	 * @param id
	 * @param ids
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> batchUpdateUserRoles(Long id, String ids);

	/**
	 * getUserByName(根据用户名查找用户)
	 * 
	 * @param loginName
	 * @return User
	 * @version 1.0.0
	 */
	User getUserByName(String loginName);

	/**
	 * 修改密码
	 * 
	 * @param map
	 * @return
	 */
	boolean resetPasswd(Map<String, Object> map);
	
	User findEntityById(Long id);

}
