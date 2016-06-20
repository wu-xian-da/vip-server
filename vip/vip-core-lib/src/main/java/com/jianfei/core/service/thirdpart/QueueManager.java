package com.jianfei.core.service.thirdpart;

import java.util.Map;

import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.dto.ServiceMsgBuilder;

public interface QueueManager {

	/**
	 * processMessage(从队列中获取信息)
	 *
	 * @param sourceQ
	 *            目标队列
	 * @param targerQ
	 *            数据备份队列
	 * @return MessageDto<Map<String,String>> 返回本次操作成功与否，和消息的消息体信息
	 * @version 1.0.0
	 */
	MessageDto<Map<String, String>> processMessage(String sourceQ,
			String targerQ);

	public static final String SMS_QUEUE_VIP_BAK = "SMS_QUEUES_VIP_BAK";

	/**
	 * 发送消息
	 * 
	 * @param msgBuilder
	 * @return
	 */
	BaseMsgInfo sendMessage(ServiceMsgBuilder msgBuilder);

}
