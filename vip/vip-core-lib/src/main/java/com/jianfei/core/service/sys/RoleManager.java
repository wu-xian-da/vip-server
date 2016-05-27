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
import com.jianfei.core.common.utils.JsonTreeData;
import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description: TODO
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
			String description, String ids, String url,Integer priority);

	/**
	 * buildRoleTreeNode(构造树)
	 * 
	 * @return List<JsonTreeData>
	 * @version 1.0.0
	 */
	List<JsonTreeData> buildRoleTreeNode();

	/**
	 * selectRoleByUserId(根据用户ID查找角色信息)
	 * 
	 * @param id
	 * @return List<Role>
	 * @version 1.0.0
	 */
	List<Role> selectRoleByUserId(Long id);

}
