package com.jianfei.core.service.thirdpart.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.jianfei.core.common.utils.HttpClientUtils;

/**
 * 测试调用一些meeting第三方接口
 * 
 */
public class TestMeetingInterface {

	private static String getSignXml() {
		String password = "123456a";
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<request>");
		sb.append("<ip></ip>");
		sb.append("<memberId></memberId>");
		sb.append("<userName>123123123</userName>");		//此处填入VIP卡号
		sb.append("<passWord>" + md5(password) + "</passWord>");  
		sb.append("<phone>13988880000</phone>");
		sb.append("<name>WANG</name>");						//此处填入用户姓名
		sb.append("<email></email>");
		sb.append("<identificationType></identificationType>");
		sb.append("<identificationNum></identificationNum>");
		sb.append("<sex></sex>");
		sb.append("<nationality></nationality>");
		sb.append("<ename></ename>");
		sb.append("<birthday></birthday>");
		sb.append("<contactAddress></contactAddress>");
		sb.append("<postCode></postCode>");
		sb.append("<passportNumber></passportNumber>");
		sb.append("<passportValid></passportValid>");
		sb.append("<regSource></regSource>");
		sb.append("</request>");

		return sb.toString();
	}
	//已和对方调试通过版本，加入vip-app的注册接口中调用。
	@Test
	public void testKitty() {

		String url = "http://121.79.144.166:8150/inf/b2c/member.shtml";
		String account = "BJYCXEBANK"; //对方系统账号
		String key = "123456a";//对方系统密码
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("account", account));
		nvps.add(new BasicNameValuePair("websiteCode", "1"));
		nvps.add(new BasicNameValuePair("data",getSignXml()));
		nvps.add(new BasicNameValuePair("service", "registration"));
		String innnerKey = md5(account + md5(key));
		StringBuffer buffer = new StringBuffer();
		buffer.append(innnerKey).append(getSignXml()).append("VETRIP_B2C");
		String sign = md5(buffer.toString());
		nvps.add(new BasicNameValuePair("sign", sign));
		nvps.add(new BasicNameValuePair("responseType", "2"));
		nvps.add(new BasicNameValuePair("version", "2"));

//		nvps.add(new BasicNameValuePair("name", "习大大"));
//		nvps.add(new BasicNameValuePair("memberId", "12345678"));
//		nvps.add(new BasicNameValuePair("contactAddress", "12345678"));
//		nvps.add(new BasicNameValuePair("postCode", "首都国际机场"));
//		nvps.add(new BasicNameValuePair("ename", "2017-03-16"));
		System.out.println(nvps.toString());
		
		String result = HttpClientUtils.post(url, nvps);
		System.out.println(result);

	}

	public static String md5(String rawPass) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(rawPass, null);
	}

	private static String getXmlInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<request>");
		sb.append("<ip></ip>");
		sb.append("<memberId></memberId>");
		sb.append("<userName>refineli</userName>");
		sb.append("<passWord>refineli</passWord>");
		sb.append("<phone>13275601668</phone>");
		sb.append("<name>admin</name>");
		sb.append("<email></email>");
		sb.append("<identificationType></identificationType>");
		sb.append("<identificationNum></identificationNum>");
		sb.append("<sex></sex>");
		sb.append("<nationality></nationality>");
		sb.append("<ename></ename>");
		sb.append("<birthday></birthday>");
		sb.append("<contactAddress></contactAddress>");
		sb.append("<postCode></postCode>");
		sb.append("<passportNumber></passportNumber>");
		sb.append("<passportValid></passportValid>");
		sb.append("<regSource></regSource>");
		sb.append("</request>");

		return sb.toString();
	}
}