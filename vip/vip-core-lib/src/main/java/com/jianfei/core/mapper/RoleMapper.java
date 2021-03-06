/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月12日-上午11:34:31
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.Role;
import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: 角色Mapper
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月12日 上午11:34:31
 * 
 * @version 1.0.0
 *
 */

@MyBatisDao
public interface RoleMapper extends BaseMapper<Role> {

	void deleteByResourceFromRole(Long roleId);

	void batchInsertRoleResource(List<Map<String, Object>> list);

	/**
	 * selectRoleByUserId(根据用户ID查找所有角色)
	 * 
	 * @param id
	 *            用户ID
	 * @return List<Role>
	 * @version 1.0.0
	 */
	List<Role> selectRoleByUserId(Long id);

	/**
	 * selectUserByRoleId(更具角色ID获取所有用户)
	 * 
	 * @param id
	 *            角色ID
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> selectUserByRoleId(Long id);

	/**
	 * selectRoleById(根据主键查找角色)
	 * 
	 * @param id
	 * @return Map<String,Object>
	 * @version 1.0.0
	 */
	Map<String, Object> selectRoleById(Long id);

	/**
	 * 根据用户工号查询用户角色信息
	 * 
	 * @param uno
	 * @return List<Map<String,Object>>
	 * @version 1.0.0
	 */
	List<Map<String, Object>> selectRoleByUserUno(String uno);

}
