package com.jianfei.core.dto.exception;

/**
 * 空港获取二维码接口异常
 * @author leoliu
 *
 */
public class GetQrcodeException extends Exception {

	private static final long serialVersionUID = 6356555115739191749L;

	public GetQrcodeException(String message) {
		super(message);
	}

}
