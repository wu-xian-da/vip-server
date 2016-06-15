package com.jianfei.core.service.thirdpart;

import java.util.HashMap;

import com.jianfei.core.common.utils.MessageDto;

public abstract class QueueManager {

	public static final String messageKey="MESSAGEKEY";

	/**
	 * 从队列中获取信息
	 * 
	 * @param sourceQ
	 * @param targerQ
	 * @return
	 */
	public abstract MessageDto<HashMap<String, Object>> brpoplpush(String sourceQ,
			String targerQ);

}
