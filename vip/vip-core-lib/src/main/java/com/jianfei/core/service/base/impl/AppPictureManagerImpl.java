/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午11:23:00
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jianfei.core.common.utils.GloabConfig;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.enu.PictureType;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.mapper.AppPictureMapper;
import com.jianfei.core.service.base.AppPictureManager;

import static com.jianfei.core.common.utils.GloabConfig.getConfig;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月23日 下午11:23:00
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class AppPictureManagerImpl implements AppPictureManager {

	@Autowired
	private AppPictureMapper appPictureMapper;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AppPictureService#save(com.jianfei.core
	 * .bean.AppPicture)
	 */
	@Override
	public MessageDto<String> save(AppPicture appPicture) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			appPictureMapper.insert(appPicture);
		} catch (Exception e) {
			logger.error("保存图片", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AppPictureService#updateByPrimaryKeySelective
	 * (com.jianfei.core.bean.AppPicture)
	 */
	@Override
	public MessageDto<String> updateByPrimaryKeySelective(AppPicture appPicture) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			appPictureMapper.updateByPrimaryKeySelective(appPicture);
		} catch (Exception e) {
			logger.error("保存图片", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AppPictureService#deleteByPrimaryKey(java
	 * .lang.Integer)
	 */
	@Override
	public MessageDto<String> deleteByPrimaryKey(Integer id) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			appPictureMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			logger.error("保存图片", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.base.AppPictureService#get(java.util.Map)
	 */
	@Override
	public MessageDto<List<AppPicture>> get(Map<String, Object> map) {
		MessageDto<List<AppPicture>> messageDto = new MessageDto<List<AppPicture>>();
		try {
			List<AppPicture> list = appPictureMapper.get(map);
			if (CollectionUtils.isEmpty(list)) {
				list = Lists.newArrayList();
			}
			messageDto.setData(list).setOk(true);
		} catch (Exception e) {
			logger.error("保存图片", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AppPictureService#selectByPrimaryKey(java
	 * .lang.Integer)
	 */
	@Override
	public MessageDto<AppPicture> selectByPrimaryKey(Integer pictureId) {
		MessageDto<AppPicture> messageDto = new MessageDto<AppPicture>();
		try {
			AppPicture appPicture = appPictureMapper
					.selectByPrimaryKey(pictureId);
			if (null != appPicture) {
				messageDto.setOk(true).setData(appPicture);
			}
		} catch (Exception e) {
			logger.error("保存图片", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MessageDto.MsgFlag.SUCCESS);
	}

	/**
	 * 查询某个类型下的轮播图数据
	 *
	 * @param pictureType
	 * @return
	 */
	@Override
	public List<AppPicture> getPicture(PictureType pictureType) {
		AppPicture appPicture=new AppPicture();
		appPicture.setImagetype(pictureType.getName());
		List<AppPicture> pictures=appPictureMapper.getPicture(appPicture);
		if (pictures==null){
			return new ArrayList<>();
		}
	   String staticIP=GloabConfig.getConfig("static.resource.server.address");
		for (AppPicture picture:pictures){
			picture.setPictureUrl(staticIP+picture.getPictureUrl());
		}
		return appPictureMapper.getPicture(appPicture);
	}
}
