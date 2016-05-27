/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午2:04:02
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

import com.jianfei.core.bean.Menu;
import com.jianfei.core.bean.Resource;
import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.security.shiro.ShiroUtils;
import com.jianfei.core.common.shrio.ShiroDbRealm;
import com.jianfei.core.common.utils.JsonTreeData;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MessageDto.MsgFlag;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.common.utils.TreeGrid;
import com.jianfei.core.common.utils.TreeNodeUtil;
import com.jianfei.core.mapper.ResourceMapper;
import com.jianfei.core.service.sys.ResourceManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午2:04:02
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class ResourceManagerImpl implements ResourceManager {

	@Autowired
	private ResourceMapper resourceMapper;
	private ShiroDbRealm shiroDbRealm = new ShiroDbRealm();
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.sys.ResourceManager#getCurrentMenus()
	 */
	@Override
	public List<Menu> getCurrentMenus() {
		List<Resource> resources = null;
		// 超级管理员
		if (ShiroUtils.getPrincipal().getUserType() == 2) {
			resources = resourceMapper.get(new MapUtils.Builder().build());
		} else {
			// 系统用户
			resources = resourceMapper.findResourceByUserId(StringUtils
					.toLong(ShiroUtils.getPrincipal().getId()));
		}
		List<Menu> menus = Menu.getSecondMenu(resources);
		return menus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.sys.ResourceManager#buildResourceTreeNode()
	 */
	@Override
	public List<JsonTreeData> buildResourceTreeNode() {
		List<Resource> resources = getAll();
		return TreeNodeUtil.buildTree(resources);
	}

	@SuppressWarnings("unchecked")
	public List<Resource> getAll() {
		List<Resource> resources = null;
		Object object = JedisUtils.getObject(CacheCons.Sys.SYS_RESOURCE_LIST);
		if (null != object) {
			resources = (List<Resource>) object;
		} else {
			resources = resourceMapper.get(new HashMap<String, Object>());
			JedisUtils.setObject(CacheCons.Sys.SYS_RESOURCE_LIST, resources, 0);
		}
		return resources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.sys.ResourceManager#buildResourceTreeGrid()
	 */
	@Override
	public TreeGrid<Map<String, Object>> buildResourceTreeGrid() {
		List<Resource> resources = getAll();
		TreeGrid<Map<String, Object>> treeGrid = TreeGrid
				.bindTreeGriid(resources);
		return treeGrid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.ResourceManager#findEntityById(java.lang
	 * .Long)
	 */
	@Override
	public MessageDto<Resource> findEntityById(Long id) {
		MessageDto<Resource> messageDto = new MessageDto<Resource>();
		Resource resource = resourceMapper.findEntityById(id);
		if (null != resource) {
			messageDto.setData(resource).setOk(true);
		}
		return messageDto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.ResourceManager#save(com.jianfei.core.bean
	 * .Resource)
	 */
	@Override
	public MessageDto<String> save(Resource resource) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			resourceMapper.save(resource);
			JedisUtils.delObject(CacheCons.Sys.SYS_RESOURCE_LIST);
			shiroDbRealm.cleanCache();
			messageDto.setOk(true).setMsgBody(MsgFlag.SUCCESS);
		} catch (Exception e) {
			logger.error("禁用用户信息：{}", e.getMessage());
			messageDto.setMsgBody(MsgFlag.ERROR);
		}
		return messageDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.ResourceManager#update(com.jianfei.core.
	 * bean.Resource)
	 */
	@Override
	public MessageDto<String> update(Resource resource) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			resourceMapper.update(resource);
			JedisUtils.delObject(CacheCons.Sys.SYS_RESOURCE_LIST);
			shiroDbRealm.cleanCache();
			messageDto.setOk(true).setMsgBody(MsgFlag.SUCCESS);
		} catch (Exception e) {
			logger.error("保存用户信息：{}", e.getMessage());
			messageDto.setMsgBody(MsgFlag.ERROR);
		}
		return messageDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.sys.ResourceManager#delete(java.lang.Long)
	 */
	@Override
	public MessageDto<String> delete(Long id) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			resourceMapper.delete(id);
			JedisUtils.delObject(CacheCons.Sys.SYS_RESOURCE_LIST);
			shiroDbRealm.cleanCache();
			messageDto.setOk(true).setMsgBody(MsgFlag.SUCCESS);

		} catch (Exception e) {
			logger.error("保存用户信息：{}", e.getMessage());
			messageDto.setMsgBody(MsgFlag.ERROR);
		}
		return messageDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.ResourceManager#findResourceByRoleId(java
	 * .lang.Long)
	 */
	@Override
	public List<Resource> findResourceByRoleId(Long id) {
		List<Resource> list = resourceMapper.findResourceByRoleId(id);
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<Resource>();
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.sys.ResourceManager#get(java.util.Map)
	 */
	@Override
	public List<Resource> get(Map<String, Object> map) {
		return resourceMapper.get(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.ResourceManager#findResourceByUserId(java
	 * .lang.Long)
	 */
	@Override
	public List<Resource> findResourceByUserId(Long id) {
		return resourceMapper.findResourceByUserId(id);
	}

}
