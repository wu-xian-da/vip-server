/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月19日-下午1:16:40
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.bean.AriPort;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.mapper.AriPortMapper;
import com.jianfei.core.mapper.UserMapper;
import com.jianfei.core.service.base.AriPortService;

/**
 *
 * @Description: 机场信息纬度
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月19日 下午1:16:40
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class AriPortServiceImpl implements AriPortService<AriPort> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.base.AriPortService#save(java.lang.Object)
	 */
	@Override
	public MessageDto<AriPort> save(AriPort t) {
		MessageDto<AriPort> dto = new MessageDto<AriPort>();
		try {
			ariPortMapper.save(t);
			dto.setOk(true).setMsgBody(MessageDto.MsgFlag.OK);
		} catch (Exception e) {
			logger.error("添加机场信息:{}", e.getMessage());
			dto.setOk(false).setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AriPortService#update(java.lang.Object)
	 */
	@Override
	public MessageDto<AriPort> update(AriPort t) {
		MessageDto<AriPort> dto = new MessageDto<AriPort>();
		try {
			ariPortMapper.update(t);
			dto.setOk(true).setMsgBody(MessageDto.MsgFlag.OK);
		} catch (Exception e) {
			logger.error("添加机场信息:{}", e.getMessage());
			dto.setOk(false).setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.base.AriPortService#get(java.util.Map)
	 */
	@Override
	public MessageDto<List<AriPort>> get(Map<String, Object> params) {
		MessageDto<List<AriPort>> dto = new MessageDto<List<AriPort>>();
		try {
			List<AriPort> ariPorts = ariPortMapper.get(params);

			dto.setData(ariPorts).setOk(true)
					.setMsgBody(MessageDto.MsgFlag.SUCCESS);

			dto.setOk(true).setMsgBody(MessageDto.MsgFlag.OK);
		} catch (Exception e) {
			logger.error("添加机场信息:{}", e.getMessage());
			dto.setOk(false).setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AriPortService#batchInsertUserAriport(java
	 * .util.List)
	 */
	@Override
	public MessageDto<String> batchInsertUserAriport(Long id, String arids) {
		MessageDto<String> dto = new MessageDto<String>();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			String[] idds = arids.split(",");
			for (String aid : idds) {
				if (!StringUtils.isEmpty(aid)) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("aid", aid);
					m.put("uid", id);
					m.put("dtflag", 0);
					m.put("userType", 0);
					mapList.add(m);
				}
			}
			ariPortMapper.deleteAriport(id);
			ariPortMapper.batchInsertUserAriport(mapList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量更新用的数据权限:{}", e.getMessage());
		}
		return dto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AriPortService#selectAriportByUserId(java
	 * .lang.Long)
	 */
	@Override
	public MessageDto<List<AriPort>> selectAriportByUserId(Long id) {
		MessageDto<List<AriPort>> dto = new MessageDto<List<AriPort>>();
		try {
			List<AriPort> ariPorts = ariPortMapper.selectAriportByUserId(id);
			dto.setData(ariPorts).setOk(true)
					.setMsgBody(MessageDto.MsgFlag.SUCCESS);
			dto.setOk(true).setMsgBody(MessageDto.MsgFlag.OK);
		} catch (Exception e) {
			logger.error("添加机场信息:{}", e.getMessage());
			dto.setOk(false).setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return dto;
	}

	@Autowired
	private AriPortMapper ariPortMapper;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AriPortService#datePermissionData(java.
	 * lang.Long)
	 */
	@Override
	public List<Map<String, Object>> datePermissionData(Long id) {
		MessageDto<List<AriPort>> messageDto = get(new MapUtils.Builder()
				.build());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (!messageDto.isOk()) {
			return list;
		}
		MessageDto<List<AriPort>> aList = selectAriportByUserId(id);
		for (AriPort ariPort : messageDto.getData()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", ariPort.getId());
			map.put("name", ariPort.getName());
			map.put("checked", false);
			for (AriPort port : aList.getData()) {
				if (ariPort.getId().equals(port.getId())) {
					map.put("checked", true);
				}
			}
			list.add(map);
		}
		return list;
	}

	@Autowired
	private UserMapper userMapper;
}
