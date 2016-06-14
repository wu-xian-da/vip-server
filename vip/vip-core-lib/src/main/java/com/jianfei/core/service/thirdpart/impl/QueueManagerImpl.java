package com.jianfei.core.service.thirdpart.impl;

import java.util.HashMap;

import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.thirdpart.QueueManager;

public class QueueManagerImpl implements QueueManager {

	@SuppressWarnings("unused")
	@Override
	public MessageDto<HashMap<String, Object>> brpoplpush(String sourceQ,
			String targerQ) {
		String rs = JedisUtils.rpoplpush("messages", "kitty");
		return null;
	}

}
