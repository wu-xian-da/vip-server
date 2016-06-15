package com.jianfei.core.service.thirdpart;

import java.util.Map;

import com.jianfei.core.common.utils.MessageDto;

public interface QueueManager {

	public static final String messageKey = "MESSAGEKEY";

	/**
	 * processMessage(从队列中获取信息)
	 * 
	 * @param sourceQ
	 *            目标队列
	 * @param targerQ
	 *            数据备份队列
	 * @return MessageDto<Map<String,String>>
	 * @version 1.0.0
	 */
	MessageDto<Map<String, String>> processMessage(String sourceQ,
			String targerQ);

}
