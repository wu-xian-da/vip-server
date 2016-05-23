/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月22日-上午10:54:29
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;
import org.apache.shiro.authc.AuthenticationException;
/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com 
 * @date: 2016年5月22日 上午10:54:29 
 * 
 * @version 1.0.0
 *
 */

public class CaptchaException extends AuthenticationException {
 
	private static final long serialVersionUID = 1L;
 
	public CaptchaException() {
 
		super();
 
	}
 
	public CaptchaException(String message, Throwable cause) {
 
		super(message, cause);
 
	}
 
	public CaptchaException(String message) {
 
		super(message);
 
	}
 
	public CaptchaException(Throwable cause) {
 
		super(cause);
 
	}
 
}