/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午3:42:57
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import java.util.List;
import java.util.Map;

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
import com.jianfei.core.common.utils.ObjectUtils;
import com.jianfei.core.common.utils.PasswdHelper;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.UserProvince;
import com.jianfei.core.mapper.BusizzMapper;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.base.BusizzManager;
import com.jianfei.core.service.sys.RoleManager;

/**
 *
 * @Description: 业务员管理
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月23日 下午3:42:57
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class BusizzManagerImpl implements BusizzManager<User> {

	@Autowired
	private BusizzMapper busizzMaapper;

	@Autowired
	private AriPortManager<AriPort> ariPortService;
	@Autowired
	private RoleManager roleManager;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.common.persistence.BaseService#save(java.io.Serializable
	 * )
	 */
	@Override
	public MessageDto<User> save(User t) {
		MessageDto<User> messageDto = new MessageDto<User>();
		try {
			busizzMaapper.save(t);
		} catch (Exception e) {
			logger.error("保存业务员信息:{}", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.common.persistence.BaseService#update(java.io.Serializable
	 * )
	 */
	@Override
	public MessageDto<User> update(User t) {
		MessageDto<User> messageDto = new MessageDto<User>();
		try {
			busizzMaapper.update(t);
		} catch (Exception e) {
			logger.error("保存业务员信息:{}", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.BusizzService#saveUser(com.jianfei.core
	 * .bean.User, java.lang.String, java.lang.String)
	 */
	@Override
	public MessageDto<String> saveUser(User user, String arids, String roleids) {
		MessageDto<String> messageDto = new MessageDto<String>();
		user.setPassword(PasswdHelper.defaultPasswdProdece(user.getSalt()));
		user.setExtraPasswd(PasswdHelper.defaultPasswdProdece());
		Long id = 0l;
		if (!StringUtils.isEmpty(user.getCode())) {
			User u = busizzMaapper.getUserByCode(StringUtils.trim(user
					.getCode()));
			// 保存操作,用户名已经存在
			if (!ObjectUtils.isEmpty(u) && 0 == user.getId()) {
				return messageDto.setMsgBody("工号已经存在,请更换...");
			} else if (ObjectUtils.isEmpty(u) && 0 == user.getId()) {
				// 保存用户
				user.setState(GloabConfig.OPEN);
				busizzMaapper.save(user);
				User u2 = busizzMaapper.getUserByCode(StringUtils.trim(user
						.getCode()));
				id = u2.getId();

			} else if (u != null && user.getId() != u.getId()) {
				// 更新操作
				return messageDto.setMsgBody("工号已经存在,请更换...");
			} else {
				busizzMaapper.update(user);
				id = user.getId();
			}
		}
		// 批量更新用户数据权限
		if (!StringUtils.isEmpty(arids)) {
			MessageDto<String> dto2 = ariPortService.batchInsertUserAriport(id,
					arids);
			if (!dto2.isOk()) {
				return dto2;
			}
		} else {
			ariPortService.deleteAirportByUserId(id);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.base.BusizzService#delete(java.lang.Long)
	 */
	@Override
	public MessageDto<String> delete(Long id) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			busizzMaapper.delete(id);
		} catch (Exception e) {
			logger.error("保存业务员信息:{}", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.base.BusizzManager#listMap(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> listMap(Map<String, Object> map) {

		return busizzMaapper.listMap(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.base.BusizzManager#initpwd(java.util.Map)
	 */
	@Override
	public MessageDto<String> initpwd(Map<String, Object> map) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			// 初始化系统后台用户密码
			String roleId = StringUtils.obj2String(map.get("roleId"));
			String salt = StringUtils.obj2String(map.get("salt"));
			Long id = StringUtils.toLong(map.get("id"));
			if (!StringUtils.isEmpty(roleId)) {
				map.put("pwd", PasswdHelper.passwdProdeceNoSalt(roleId,
						roleManager, id));
				map.put("password", PasswdHelper.passwdProdece(roleId,
						roleManager, id,
						StringUtils.obj2String(map.get("salt"))));
				busizzMaapper.initpwd(map);
				return messageDto.setOk(true).setMsgBody(
						MessageDto.MsgFlag.SUCCESS);
			}

			// 初始化业务员密码
			if (StringUtils.isEmpty(salt)) {
				return messageDto.setMsgBody(MsgFlag.ERROR);
			} else {
				map.put("password", PasswdHelper.defaultPasswdProdece(salt));
			}
			map.put("pwd", PasswdHelper.defaultPasswdProdece());

			busizzMaapper.initpwd(map);
		} catch (Exception e) {
			logger.error("初始化业务员信息:{}", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.base.BusizzManager#selectMap(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> selectMap(Map<String, Object> map) {
		return busizzMaapper.selectMap(map);
	}

	/**
	 * 根据业务人员id获取所属省份id
	 */
	@Override
	public List<UserProvince> getProvinceIdByUserId(String uNo) {
		// TODO Auto-generated method stub
		return busizzMaapper.getProvinceIdByUserId(uNo);
	}
}
