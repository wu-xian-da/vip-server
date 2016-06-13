package com.jianfei.listener;

import java.util.Date;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.GloabConfig;

public class CustomerSessionListener implements SessionListener {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onStart(Session session) {
		User user = (User) session.getAttribute(GloabConfig.SESSION_USER);
		logger.info(user.getName() + "  加入会话.  " + new Date());
	}

	@Override
	public void onStop(Session session) {
		User user = (User) session.getAttribute(GloabConfig.SESSION_USER);
		logger.info(user.getName() + "  退出会话.  " + new Date());

	}

	@Override
	public void onExpiration(Session session) {

	}

}
