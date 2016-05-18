/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午3:37:42
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.Menu;
import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.Role;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.cache.CacheUtils;
import com.jianfei.core.common.security.shiro.ShiroUtils;
import com.jianfei.core.common.utils.Grid;
import com.jianfei.core.common.utils.JsonTreeData;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.common.utils.TreeGrid;
import com.jianfei.core.common.utils.TreeNodeUtil;
import com.jianfei.core.mapper.ResourceMapper;
import com.jianfei.core.mapper.RoleMapper;
import com.jianfei.core.mapper.UserMapper;

/**
 *
 * @Description: 系统资源操作
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月15日 下午3:37:42
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class SystemService {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String CURRENT_MENU = "currentMenus";
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	

	public List<Menu> getCurrentMenus() {
		List<Resource> resources = resourceMapper
				.findResourceByUserId(StringUtils.toLong(ShiroUtils
						.getPrincipal().getId()));
		List<Menu> menus = Menu.getSecondMenu(resources);
		return menus;
	}

	public List<JsonTreeData> buildResourceTreeNode() {
		List<Resource> resources = resourceMapper
				.get(new HashMap<String, Object>());
		return TreeNodeUtil.buildTree(resources);
	}

	/**
	 * buildRoleTreeNode(构造树)
	 * 
	 * @return List<JsonTreeData>
	 * @version 1.0.0
	 */
	public List<JsonTreeData> buildRoleTreeNode() {
		List<Role> roles = roleMapper.get(new HashMap<String, Object>());
		return TreeNodeUtil.buildRoleTree(roles);
	}

	/**
	 * buildResourceTreeGrid(构建树形资源表格)
	 * 
	 * @return TreeGrid<Map<String,Object>>
	 * @version 1.0.0
	 */
	public TreeGrid<Map<String, Object>> buildResourceTreeGrid() {
		List<Resource> resources = resourceMapper
				.get(new HashMap<String, Object>());
		TreeGrid<Map<String, Object>> treeGrid = TreeGrid
				.bindTreeGriid(resources);
		return treeGrid;
	}

	/**
	 * updateRoleResource(更新角色资源)
	 * 
	 * @param id
	 * @param ids
	 * @return MessageDto
	 * @version 1.0.0
	 */
	public MessageDto updateRoleResource(Long id, String ids) {
		MessageDto dto = new MessageDto();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			roleMapper.deleteByResourceFromRole(id);
			String[] strings = ids.split(",");
			for (String str : strings) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("roleId", id);
				map.put("resourceId", str);
				list.add(map);
			}
			roleMapper.batchInsertRoleResource(list);
		} catch (Exception e) {
			logger.error("更新用户角色:{}", e.getMessage());
			return dto.setMsgBody("操作失败，请稍后重试...");
		}
		return dto.setOk(true).setMsgBody("更新成功...");
	}

	/**
	 * batchUpdateUserRoles(这里用一句话描述这个方法的作用) void
	 * 
	 * @version 1.0.0
	 */
	public MessageDto batchUpdateUserRoles(Long id, String ids) {
		MessageDto dto = new MessageDto();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			userMapper.deleteRolesFromUser(id);
			String[] strings = ids.split(",");
			for (String str : strings) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", id);
				map.put("roleId", str);
				list.add(map);
			}
			CacheUtils.remove(CacheUtils.BROKER_CACHE, CURRENT_MENU);
		} catch (Exception e) {
			logger.error("更新用户角色:{}", e.getMessage());
			return dto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		userMapper.batchInsertUserRole(list);
		return dto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	public MessageDto saveResourc(Resource resource) {
		MessageDto dto = new MessageDto();
		try {
			resourceMapper.save(resource);
		} catch (Exception e) {
			logger.error("保存资源:{}", e.getMessage());
			return dto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return dto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/**
	 * queryPage(分页查询)
	 *
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 *            封装查询参数
	 * @return Page<Role>
	 * @version 1.0.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<Role> simplePage(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<Role> list = roleMapper.get(params);
		PageInfo<Role> pageInfo = new PageInfo(list);
		return pageInfo;
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	
	public RoleMapper getRoleMapper() {
		return roleMapper;
	}



	public ResourceMapper getResourceMapper() {
		return resourceMapper;
	}


	ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Grid bindUserGridData(PageInfo<User> pageInfo) {
		List<User> list = pageInfo.getList();
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (User user : list) {
			Map<String, Object> map = MapUtils.<User> entityInitMap(user);
			maps.add(map);
		}
		Grid grid = new Grid();
		grid.setRows(maps);
		grid.setTotal(pageInfo.getTotal());
		return grid;
	}

}