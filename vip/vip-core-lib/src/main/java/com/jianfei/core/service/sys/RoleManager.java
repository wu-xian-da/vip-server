/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午1:32:29
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.sys;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.Role;
import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description: 角色管理
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午1:32:29
 * 
 * @version 1.0.0
 *
 */
public interface RoleManager {

	/**
	 * get(查找角色信息)
	 * 
	 * @param params
	 * @return MessageDto<List<User>>
	 * @version 1.0.0
	 */
	MessageDto<List<Role>> get(Map<String, Object> params);

	/**
	 * update(更新角色信息)
	 * 
	 * @param role
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> update(Role role);

	/**
	 * updateRoleResource(更新角色权限)
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param ids
	 * @return MessageDto<String>
	 * @version 1.0.0
	 */
	MessageDto<String> updateRoleResource(Long id, String name,
			String description, String ids, String url, String initPwd,
			String roleType);

	/**
	 * selectRoleByUserId(根据用户ID查找角色信息)
	 * 
	 * @param id
	 * @return List<Role>
	 * @version 1.0.0
	 */
	List<Role> selectRoleByUserId(Long id);

	/**
	 * selectRoleById(根据主键查找角色)
	 * 
	 * @param id
	 * @return Map<String,Object>
	 * @version 1.0.0
	 */
	Map<String, Object> selectRoleById(Long id);

	List<Role> getAll();
}
