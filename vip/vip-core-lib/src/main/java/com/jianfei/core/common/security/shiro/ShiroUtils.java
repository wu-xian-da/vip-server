package com.jianfei.core.common.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.jianfei.core.common.shrio.ShiroDbRealm.Principal;

/**
 *
 * @Description: shrio工具类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月16日 下午3:29:33
 * 
 * @version 1.0.0
 *
 */
public class ShiroUtils {

	public static Principal getPrincipal() {
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		if (null != subject && null != subject.getPrincipal()) {
			return (Principal) subject.getPrincipal();
		}
		return null;
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
		} catch (InvalidSessionException e) {
		}
		return null;
	}

	/**
	 * remove Session Attribute
	 * 
	 * @param key
	 */
	public static void removeSessionArrtibute(Object key) {
		getSession().removeAttribute(key);
	}

	/**
	 * set Session Attribute
	 * 
	 * @param key
	 * @param value
	 */
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * get Session Attribute
	 * 
	 * @param key
	 */
	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}
}
