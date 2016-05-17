/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-上午11:28:31
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import org.apache.shiro.authc.AccountException;

/**
 * Thrown when attempting to authenticate with a principal that doesn't exist in the system (e.g.
 * by specifying a username that doesn't relate to a user account).
 *
 * <p>Whether or not an application wishes to alert a user logging in to the system of this fact is
 * at the discretion of those responsible for designing the view and what happens when this
 * exception occurs.
 *
 * @since 0.1
 */
public class ValidateCodeException extends AccountException {

	/**
     * Creates a new UnknownAccountException.
     */
    public ValidateCodeException() {
        super();
    }

    /**
     * Constructs a new UnknownAccountException.
     *
     * @param message the reason for the exception
     */
    public ValidateCodeException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnknownAccountException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public ValidateCodeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UnknownAccountException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public ValidateCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
