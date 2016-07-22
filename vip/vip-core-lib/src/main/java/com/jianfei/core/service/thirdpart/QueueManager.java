package com.jianfei.core.service.thirdpart;

import java.util.Map;

import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.dto.ServiceMsgBuilder;

/**
 *
 * @Description: 队列处理接口
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月18日 下午1:47:25
 * 
 * @version 1.0.0
 *
 */
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

	/**
	 * 发送消息
	 * 
	 * @param msgBuilder
	 * @return
	 */
	boolean sendMessage(ServiceMsgBuilder msgBuilder);

	public static final String SMS_QUEUE_VIP_BAK = "SMS_QUEUES_VIP_BAK";
	public static final String MESSAGEKEY = "SMS_QUEUE_VIP";

}
