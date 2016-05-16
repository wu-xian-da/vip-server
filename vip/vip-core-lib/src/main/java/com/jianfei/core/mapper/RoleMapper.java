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

import com.jianfei.core.bean.Role;
import com.jianfei.core.common.persistence.MyBatisDao;

/**
 *
 * @Description: TODO
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

	List<Role> selectRoleByUserId(Long id);
	
}
