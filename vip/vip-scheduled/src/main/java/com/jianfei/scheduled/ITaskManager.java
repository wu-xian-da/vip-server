package com.jianfei.scheduled;

/**
 *
 * @Description: 定时任务管理器
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月28日 下午2:35:14
 * 
 * @version 1.0.0
 *
 */
public interface ITaskManager {

	/**
	 * 订单每日归档
	 * 
	 * @version 1.0.0
	 */
	void dailyOrderArchice();

	/**
	 * 定时获取空港的核销数据 每小时获取一次
	 * 
	 * @version 1.0.0
	 */
	void checkinDataSchedule();

	/**
	 * 从消息队列中拉消息<br>
	 * 每1秒执行一次
	 * 
	 * @version 1.0.0
	 */
	void pullSmsMessage();

	/**
	 * 验证订单状态是否有效，如果无效更改订单状态为无效状态，每隔5分钟扫描一次，服务器重启延迟25分钟校验
	 */
	void validateOrdereIsEfective();
	
	/**
	 * 检查vip卡是否已过期
	 */
	void checkExpiredOfCard();
}
