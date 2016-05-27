/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午1:10:47
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MessageDto.MsgFlag;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.mapper.UserMapper;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.sys.UserManaer;

/**
 *
 * @Description: 用户管理
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午1:10:47
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class UserManagerImpl implements UserManaer<User> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserMapper userMapper;

	private AriPortManager<AriPort> ariPortManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.common.persistence.BaseService#get(java.util.Map)
	 */
	@Override
	public MessageDto<List<User>> get(Map<String, Object> params) {
		MessageDto<List<User>> messageDto = new MessageDto<List<User>>();
		// 从缓存中获取
		List<User> list = userMapper.get(params);
		if (!CollectionUtils.isEmpty(list)) {
			messageDto.setData(list).setOk(true);
		}

		return messageDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.sys.UserManaer#delete(java.lang.Long)
	 */
	@Override
	public MessageDto<String> delete(Long id) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			// 删除用户
			userMapper.delete(id);
			// 删除该用户对应的角色信息
			userMapper.deleteRolesFromUser(id);
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
	 * com.jianfei.core.service.sys.UserManaer#saveUser(com.jianfei.core.bean
	 * .User, java.lang.String, java.lang.String)
	 */
	@Override
	public MessageDto<String> saveUser(User user, String arids, String roleids) {
		MessageDto<String> messageDto = new MessageDto<String>();
		SimpleHash simpleHash = new SimpleHash("md5",
				GloabConfig.getConfig("defalut.passwd"), user.getSalt());
		user.setPassword(simpleHash.toString());
		Long id = 0l;
		if (!StringUtils.isEmpty(user.getLoginName())) {
			User u = userMapper.getUserByName(StringUtils.trim(user
					.getLoginName()));
			// 保存操作,用户名已经存在
			if (null != u && 0 == user.getId()) {
				return messageDto.setMsgBody("用户名已经存在,请更换用户名...");
			} else if (null == u && 0 == user.getId()) {
				// 保存用户
				userMapper.save(user);
				User u2 = userMapper.getUserByName(user.getLoginName());
				id = u2.getId();
			} else if (u != null && user.getId() != u.getId()) {
				// 更新操作
				return messageDto.setMsgBody("用户名已经存在,请更换用户名...");
			} else {
				userMapper.update(user);
				id = user.getId();
			}
		}
		// 更新用户角色
		if (!StringUtils.isEmpty(roleids)) {
			MessageDto<String> dto = batchUpdateUserRoles(id, roleids);
			if (!dto.isOk()) {
				return dto;
			}
		}

		// 批量更新用户数据权限
		if (!StringUtils.isEmpty(arids)) {
			MessageDto<String> dto2 = ariPortManager.batchInsertUserAriport(id,
					arids);
			if (!dto2.isOk()) {
				return dto2;
			}
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.UserManaer#batchUpdateUserRoles(java.lang
	 * .Long, java.lang.String)
	 */
	@Override
	public MessageDto<String> batchUpdateUserRoles(Long id, String ids) {
		MessageDto<String> dto = new MessageDto<String>();
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
			userMapper.batchInsertUserRole(list);
		} catch (Exception e) {
			logger.error("更新用户角色:{}", e.getMessage());
			return dto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}

		return dto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.sys.UserManaer#getUserByName(java.lang.String)
	 */
	@Override
	public User getUserByName(String loginName) {
		return userMapper.getUserByName(loginName);
	}
}
