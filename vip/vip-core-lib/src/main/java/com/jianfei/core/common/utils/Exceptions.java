/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.jianfei.core.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.CredentialsException;

/**
 * 关于异常的工具类.
 * 
 * @author calvin
 * @version 2013-01-15
 */
public class Exceptions {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		if (e == null) {
			return "";
		}
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Exception ex,
			Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}

	/**
	 * 在request中获取异常类
	 * 
	 * @param request
	 * @return
	 */
	public static Throwable getThrowable(HttpServletRequest request) {
		Throwable ex = null;
		if (request.getAttribute("exception") != null) {
			ex = (Throwable) request.getAttribute("exception");
		} else if (request.getAttribute("javax.servlet.error.exception") != null) {
			ex = (Throwable) request
					.getAttribute("javax.servlet.error.exception");
		}
		return ex;
	}

	public class ValidateCodeError extends AccountException {

		/**
		 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
		 *
		 * @version 1.0.0
		 */

		private static final long serialVersionUID = -9074513961177730516L;

		private String message;

		/**
		 * 创建一个新的实例 ValidateCodeError.
		 *
		 * @param message
		 */
		public ValidateCodeError(String message) {
			super();
			this.message = message;
		}

		/**
		 * message
		 *
		 * @return the message
		 * @version 1.0.0
		 */

		public String getMessage() {
			return message;
		}

		/**
		 * @param message
		 *            the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}

	}

}
