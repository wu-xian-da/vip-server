/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午1:33:31
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.bean.Role;
import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.shrio.ShiroDbRealm;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MessageDto.MsgFlag;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.mapper.RoleMapper;
import com.jianfei.core.service.sys.RoleManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午1:33:31
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class RoleManagerImpl implements RoleManager {

	@Autowired
	private RoleMapper roleMapper;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private ShiroDbRealm shiroDbRealm = new ShiroDbRealm();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.sys.RoleManager#get(java.util.Map)
	 */
	@Override
	public MessageDto<List<Role>> get(Map<String, Object> params) {
		MessageDto<List<Role>> messageDto = new MessageDto<List<Role>>();
		List<Role> list = roleMapper.get(params);
		if (!CollectionUtils.isEmpty(list)) {
			messageDto.setData(list).setOk(true);
		}
		return messageDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.RoleManager#update(com.jianfei.core.bean
	 * .Role)
	 */
	@Override
	public MessageDto<String> update(Role role) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			List<Map<String, Object>> maps = roleMapper.selectUserByRoleId(role
					.getId());
			if (!CollectionUtils.isEmpty(maps)) {
				return messageDto.setMsgBody("该角色已分配用户,不能删除");
			}
			roleMapper.update(role);
			messageDto.setOk(true).setMsgBody(MsgFlag.SUCCESS);
			JedisUtils.delObject(CacheCons.Sys.SYS_ROLE_LIST);
			shiroDbRealm.cleanCache();
		} catch (Exception e) {
			logger.error("更新用户信息失败:{}", e.getMessage());
			messageDto.setMsgBody(MsgFlag.ERROR);
		}
		return messageDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.RoleManager#updateRoleResource(java.lang
	 * .Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public MessageDto<String> updateRoleResource(Long id, String name,
			String description, String ids, String url, String initPwd,
			String roleType) {
		MessageDto<String> dto = new MessageDto<String>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Role role = new Role();
			role.setName(name);
			role.setDescription(description);
			role.setDtflag(GloabConfig.OPEN);
			role.setUrl(url);
			role.setInitPwd(initPwd);
			role.setRoleType(roleType);
			if (StringUtils.isEmpty(name)) {
				return dto.setMsgBody("操作失败，请稍后重试...");
			}
			if (null == id || id == 0) {
				// 查询角色名是否已经存在
				List<Role> exRoles = roleMapper.get(new MapUtils.Builder()
						.setKeyValue("notLikeName", name).build());
				if (!CollectionUtils.isEmpty(exRoles)) {
					return dto.setMsgBody("角色名已经存在，请更换...");
				}
				// 保存操作
				roleMapper.save(role);
				List<Role> roles = roleMapper.get(new MapUtils.Builder()
						.setKeyValue("notLikeName", role.getName()).build());
				if (!CollectionUtils.isEmpty(roles)) {
					id = roles.get(0).getId();
				}
			}
			// 更新角色权限
			if (id != null && 0 != id) {
				role.setId(id);
				roleMapper.update(role);
				roleMapper.deleteByResourceFromRole(id);
			}
			if (id != null && 0 != id && !StringUtils.isEmpty(ids)) {
				String[] strings = ids.split(",");
				for (String str : strings) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("roleId", id);
					map.put("resourceId", str);
					list.add(map);
				}
				roleMapper.batchInsertRoleResource(list);
			}
			JedisUtils.delObject(CacheCons.Sys.SYS_ROLE_LIST);
			shiroDbRealm.cleanCache();
		} catch (Exception e) {
			logger.error("添加，更新角色信息并授权:{}", e.getMessage());
			return dto.setMsgBody("操作失败，请稍后重试...");
		}
		return dto.setOk(true).setMsgBody("更新成功...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.RoleManager#selectRoleByUserId(java.lang
	 * .Long)
	 */
	@Override
	public List<Role> selectRoleByUserId(Long id) {
		return roleMapper.selectRoleByUserId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.RoleManager#selectRoleById(java.lang.Long)
	 */
	@Override
	public Map<String, Object> selectRoleById(Long id) {
		return roleMapper.selectRoleById(id);
	}

	@Override
	public List<Role> getAll() {
		Object object = JedisUtils.getObject(CacheCons.Sys.SYS_ROLE_LIST);
		if (null != object) {
			return (List<Role>) object;
		}
		MessageDto<List<Role>> messageDto = get(new MapUtils.Builder().build());
		if (messageDto.isOk()) {
			JedisUtils.setObject(CacheCons.Sys.SYS_ROLE_LIST,
					messageDto.getData(), 0);
		}
		return messageDto.getData();
	}
}
