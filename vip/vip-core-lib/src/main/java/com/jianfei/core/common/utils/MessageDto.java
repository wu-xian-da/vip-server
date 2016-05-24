/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月11日-下午5:49:48
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @Description: 消息传递
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月11日 下午5:49:48
 * 
 * @version 1.0.0
 *
 */
public class MessageDto<T> implements Serializable {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @version 1.0.0
	 */

	private static final long serialVersionUID = -4118317767387328525L;

	private boolean isOk;

	private String msgBody;

	private T data;

	public boolean isOk() {
		return isOk;
	}

	public MessageDto<T> setOk(boolean isOk) {
		this.isOk = isOk;
		return this;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public MessageDto<T> setMsgBody(String msgBody) {
		this.msgBody = msgBody;
		return this;
	}

	/**
	 * data
	 *
	 * @return the data
	 * @version 1.0.0
	 */

	public T getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public MessageDto<T> setData(T data) {
		this.data = data;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public static interface MsgFlag {

		public static final String SUCCESS = "操作成功...";
		public static final String ERROR = "操作失败,请稍后重试...";

		public static final String OK = "true";
		public static final String FAIL = "error";

	}
}
