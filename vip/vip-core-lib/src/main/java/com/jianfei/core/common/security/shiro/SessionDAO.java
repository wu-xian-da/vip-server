/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月14日-下午10:10:31
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.security.shiro;

import java.util.Collection;

import org.apache.shiro.session.Session;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月14日 下午10:10:31
 * 
 * @version 1.0.0
 *
 */

public interface SessionDAO extends org.apache.shiro.session.mgt.eis.SessionDAO {

	/**
	 * 获取活动会话
	 * 
	 * @param includeLeave
	 *            是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @return
	 */
	public Collection<Session> getActiveSessions(boolean includeLeave);

	/**
	 * 获取活动会话
	 * 
	 * @param includeLeave
	 *            是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @param principal
	 *            根据登录者对象获取活动会话
	 * @param filterSession
	 *            不为空，则过滤掉（不包含）这个会话。
	 * @return
	 */
	public Collection<Session> getActiveSessions(boolean includeLeave,
			Object principal, Session filterSession);

}
