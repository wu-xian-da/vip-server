package com.jianfei.core.service.thirdpart;

import java.util.HashMap;

import com.jianfei.core.common.utils.MessageDto;

public interface QueueManager {

	/**
	 * 同队列中获取信息
	 * 
	 * @param sourceQ
	 * @param targerQ
	 * @return
	 */
	MessageDto<HashMap<String, Object>> brpoplpush(String sourceQ,
			String targerQ);

}
