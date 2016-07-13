/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午11:23:00
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.PictureType;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.mapper.AppPictureMapper;
import com.jianfei.core.service.base.AppPictureManager;

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
	 * 
	 */
	@Override
	public MessageDto<String> save(AppPicture appPicture) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			appPictureMapper.insert(appPicture);
			// 清除缓存
			JedisUtils.delObject("APP_PICTURE_" + appPicture.getImagetype());
		} catch (Exception e) {
			e.printStackTrace();
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
			// 清除缓存
			JedisUtils.delObject("APP_PICTURE_" + appPicture.getImagetype());
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
			AppPicture appPicture = appPictureMapper.selectByPrimaryKey(id);
			if (null != appPicture) {
				// 清除缓存
				JedisUtils
						.delObject("APP_PICTURE_" + appPicture.getImagetype());
			}
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
		// 缓存KEY设计 APP_PICTURE_
		// 1、查询缓存是否存在
		List<AppPicture> appPictures = (List<AppPicture>) (Object) JedisUtils
				.getObject("APP_PICTURE_" + pictureType.getName());

		// 2、不存在查询数据库
		if (appPictures == null) {
			AppPicture appPicture = new AppPicture();
			appPicture.setImagetype(pictureType.getName());
			appPicture.setDtflag(0);// 存在的图片
			appPictures = appPictureMapper.getPicture(appPicture);
			AppPicture.getStaticAdderss(appPictures);
			// 3、查询结果放入缓存并返回
			JedisUtils.setObject("APP_PICTURE_" + pictureType.getName(),
					appPictures, 0);
		}
		return appPictures;
	}

	/**
	 * 更新vip室图片信息
	 */
	@Override
	public int updateByVipRoomId(Map<String, Object> map) {
		appPictureMapper.updateByVipRoomId(map);
		return 0;
	}

	/**
	 * 根据viproomId查询是否有记录
	 */
	@Override
	public List<AppPicture> selByVipRoomId(String viproomId) {
		// TODO Auto-generated method stub
		return appPictureMapper.selByVipRoomId(viproomId);
	}
}
