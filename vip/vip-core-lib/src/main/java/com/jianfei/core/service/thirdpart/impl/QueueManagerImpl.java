package com.jianfei.core.service.thirdpart.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MessageDto.MsgFlag;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.thirdpart.AirportEasyManager;
import com.jianfei.core.service.thirdpart.QueueManager;

public class QueueManagerImpl extends QueueManager {
/**
 *
 * @Description: 队列消息管理实现类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月14日 下午5:52:22
 *
 * @version 1.0.0
 *
 */
@Service
public class QueueManagerImpl implements QueueManager {

	@Autowired
	private AirportEasyManager airportEasyManager;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public MessageDto<HashMap<String, Object>> brpoplpush(String sourceQ,
			String targerQ) {
		String rs = JedisUtils.rpoplpushQ(sourceQ, targerQ);
		if (StringUtils.isEmpty(rs)) {
			return new MessageDto<HashMap<String, Object>>()
					.setMsgBody(MsgFlag.FAIL);
		}
		return null;
	}

	public boolean validateMSgType(String msgType) {
		return true;
	}

}
