package com.alipay.api.request;

import com.alipay.api.domain.AlipayOfflineSaleleadsInfoQuerybypageModel;
import java.util.Map;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayOfflineSaleleadsInfoQuerybypageResponse;

/**
 * ALIPAY API: alipay.offline.saleleads.info.querybypage request
 * 
 * @author auto create
 * @since 1.0, 2016-01-27 19:26:05
 */
public class AlipayOfflineSaleleadsInfoQuerybypageRequest implements AlipayRequest<AlipayOfflineSaleleadsInfoQuerybypageResponse> {

	private AlipayHashMap udfParams; // add user-defined text parameters
	private String apiVersion="1.0";

	/** 
	* 服务商按照条件分页查询leads信息
	 */
	private String bizContent;

	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}
	public String getBizContent() {
		return this.bizContent;
	}
	private String terminalType;
	private String terminalInfo;	
	private String prodCode;
	private String notifyUrl;

	public String getNotifyUrl() {
		return this.notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getApiVersion() {
		return this.apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public void setTerminalType(String terminalType){
		this.terminalType=terminalType;
	}

    public String getTerminalType(){
    	return this.terminalType;
    }

    public void setTerminalInfo(String terminalInfo){
    	this.terminalInfo=terminalInfo;
    }

    public String getTerminalInfo(){
    	return this.terminalInfo;
    }	

	public void setProdCode(String prodCode) {
		this.prodCode=prodCode;
	}

	public String getProdCode() {
		return this.prodCode; 
	}

	public String getApiMethodName() {
		return "alipay.offline.saleleads.info.querybypage";
	}

	public Map<String, String> getTextParams() {		
		AlipayHashMap txtParams = new AlipayHashMap();
		txtParams.put("biz_content", this.bizContent);
		if(udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public void putOtherTextParam(String key, String value) {
		if(this.udfParams == null) {
			this.udfParams = new AlipayHashMap();
		}
		this.udfParams.put(key, value);
	}

	public Class<AlipayOfflineSaleleadsInfoQuerybypageResponse> getResponseClass() {
		return AlipayOfflineSaleleadsInfoQuerybypageResponse.class;
	}
}
