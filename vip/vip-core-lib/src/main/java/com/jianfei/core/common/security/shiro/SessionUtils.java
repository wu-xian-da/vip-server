package com.jianfei.core.common.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;



public class SessionUtils {
    
    public static Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
        }
        catch (InvalidSessionException e){
        }
        return null;
    }

    /**
     * 获取当前登陆用户
     * @return
     */
	// public static ShiroUser getUser(){
	// return (ShiroUser)SecurityUtils.getSubject().getPrincipal();
	// }
    
    
    
    /**
     * remove Session Attribute
     * @param key
     */
    public static void removeSessionArrtibute(Object key){
        getSession().removeAttribute(key);
    }
    
    /**
     * set Session Attribute
     * @param key
     * @param value
     */
    public static void setSessionAttribute(Object key, Object value){
        getSession().setAttribute(key, value);
    }
    
    /**
     * get Session Attribute
     * @param key
     */
    public static Object getSessionAttribute(Object key){
        return getSession().getAttribute(key);
    }
}
