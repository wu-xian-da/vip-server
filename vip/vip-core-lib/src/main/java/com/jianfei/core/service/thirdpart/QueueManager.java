package com.jianfei.core.service.thirdpart;

import java.util.HashMap;

import com.jianfei.core.common.utils.MessageDto;

/**
 *
 * @Description: 队列消息管理
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月14日 下午5:52:07
 * 
 * @version 1.0.0
 *
 */
public interface QueueManager {

	/**
	 * 从队列中获取信息
	 * 
	 * @param sourceQ
	 * @param targerQ
	 * @return
	 */
	MessageDto<HashMap<String, Object>> brpoplpush(String sourceQ,
			String targerQ);

}
