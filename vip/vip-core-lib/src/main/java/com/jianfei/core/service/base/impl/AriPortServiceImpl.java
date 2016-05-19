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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.mapper.AriPortMapper;
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
	public MessageDto<String> batchInsertUserAriport(String formJson, Long uid,
			Integer dtflag, Integer userType) {
		List<Map<String, Object>> mapList = handBatchInsert(formJson, uid,
				dtflag, userType);
		MessageDto<String> dto = new MessageDto<String>();
		try {
			ariPortMapper.deleteAriport(uid);
			ariPortMapper.batchInsertUserAriport(mapList);
			dto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
		} catch (Exception e) {
			logger.error("添加机场信息:{}", e.getMessage());
			dto.setOk(false).setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return dto;

	}

	/**
	 * handBatchInsert(这里用一句话描述这个方法的作用)
	 * 
	 * @param formJson
	 *            void
	 * @version 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> handBatchInsert(String formJson, Long uid,
			Integer dtflag, Integer userType) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, Object> map = objectMapper.readValue(formJson,
					Map.class);
			Object object = map.get("air_port_data");
			if (object != null && !StringUtils.isEmpty(object.toString())) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				String jsonRs = objectMapper.writeValueAsString(object);
				list = objectMapper.readValue(jsonRs, list.getClass());
				for (Map<String, Object> map2 : list) {
					Map<String, Object> m = new HashMap<String, Object>();
					if (null != map2.get("id")
							&& !StringUtils.isEmpty(map2.get("id").toString())
							&& Boolean.valueOf(map2.get("checked").toString())) {
						m.put("aid", map2.get("id"));
						m.put("uid", uid);
						m.put("dtflag", dtflag);
						m.put("userType", userType);
						mapList.add(m);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapList;
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
}
