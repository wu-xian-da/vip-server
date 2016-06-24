package com.jianfei.core.common.utils;

import cn.emay.sdk.client.api.Client;

public class EmaySMSUtils {
	
	public static final String EMAY_SERIA_NO = "9SDK-EMY-0999-JDQSP";
    public static final String EMAY_KEY = "842882";
    
    private static Client client = null;
	
	public synchronized static Client getClient(){
		if(client == null){
			try {
				client=new Client(EMAY_SERIA_NO, EMAY_KEY);
			} catch (Exception e) {
				//注册emay失败
				e.printStackTrace();
			}
		}
		return client;
	}
	
	public static void main(String[] args) {
		Client client = EmaySMSUtils.getClient();
		int result = client.sendSMS(new String[] {"13966727871"}, "你好你好啊", 1);//发送即时短信
		System.out.println("result=" + result);
	}
	
}
