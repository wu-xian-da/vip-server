
package com.jianfei.frame.support;

import jsr166e.ConcurrentHashMapV8;

import java.util.Map;
import java.util.UUID;

/**
 * Create on @2014年8月5日 @上午10:08:40 
 * @author libinsong1204@gmail.com
 */
public class RestContext {
	private Map<String, Object> params = new ConcurrentHashMapV8<String, Object>();
	private MethodRequestInfo methodRequestInfo;
	private String traceId = UUID.randomUUID().toString();
	private int callCycoreCount;
	private long callCycoreTime;

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public Object getParam(String key) {
		return params.get(key);
	}

	public void addParam(String key, Object value) {
		params.put(key, value);
	}

	public int getCallCycoreCount() {
		return callCycoreCount;
	}

	public void incrCallCycoreCount() {
		this.callCycoreCount++;
	}

	public long getCallCycoreTime() {
		return callCycoreTime;
	}

	public void addCallCycoreTime(long execTime) {
		this.callCycoreTime = this.callCycoreTime + execTime;
	}

	public MethodRequestInfo getMethodRequestInfo() {
		return methodRequestInfo;
	}

	public void setMethodRequestInfo(MethodRequestInfo methodRequestInfo) {
		this.methodRequestInfo = methodRequestInfo;
	}
}
