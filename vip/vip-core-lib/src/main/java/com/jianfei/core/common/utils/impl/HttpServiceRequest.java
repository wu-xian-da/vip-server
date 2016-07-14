package com.jianfei.core.common.utils.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.jianfei.core.common.utils.ServiceRequest;

/**
 * httpClient封装类
 * @author leoliu
 *
 */
public class HttpServiceRequest implements ServiceRequest {
	private static Log log = LogFactory.getLog(HttpServiceRequest.class);

	//连接超时时间，默认10秒
    private int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private int connectTimeout = 30000;
    
    //请求器的配置
    private RequestConfig requestConfig;
    
    //HTTP请求器
    private CloseableHttpClient httpClient = null;
    
    private static HttpServiceRequest request;
    
    
	private HttpServiceRequest() {
		httpClient = HttpClients.createDefault();
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
	}
	
	public static HttpServiceRequest getInstance(){
		if (request == null)
			request = new HttpServiceRequest();
		return request;
	}

/**
 * 发送post请求
 */
	@Override
	public String sendPost(String url, Map<String,String> sendParam)
			throws UnrecoverableKeyException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
			String result = null;
	        HttpPost httppost = new HttpPost(url);  
	        // 创建参数队列    
	        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//	        Iterator<Entry<String, String>> iter = sendParam.entrySet().iterator();
//	        while (iter.hasNext()){
//	        	Entry<String,String> entry = iter.next();
//	        	formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//	        }
	        Set<Entry<String, String>> entrySet = sendParam.entrySet();
	        for (Entry<String, String> entry:entrySet){
	        	formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	        }
	        
	        UrlEncodedFormEntity uefEntity;  
	        try {  
	            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
	            httppost.setEntity(uefEntity);  
	            System.out.println("executing request " + httppost.getURI());  
	            httppost.setConfig(requestConfig);
	            CloseableHttpResponse response = httpClient.execute(httppost);  
	            try {  
	                HttpEntity entity = response.getEntity();  
	                if (entity != null) {  
	                    result = EntityUtils.toString(entity, "UTF-8");
	                }  
	            } finally {  
	                response.close();  
	            }  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (UnsupportedEncodingException e1) {  
	            e1.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	        	httppost.releaseConnection();
	        }  
	        return result;
	}

	@Override
	public String sendGet(String url) {
		String result = null;
		HttpGet httpGet = new HttpGet(url);  
        try {  
            System.out.println("executing request " + httpGet.getURI());  
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpGet);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    result = EntityUtils.toString(entity, "UTF-8");
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
        	httpGet.releaseConnection();
        }  
        return result;
	}
	
	public String sendPostXml(String url,String xmlData) {
		String result = null;
        HttpPost httppost = new HttpPost(url);  
        
        try {  
        	StringEntity ReqEntity = new StringEntity(xmlData);
            httppost.setEntity(ReqEntity);  
            httppost.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    result = EntityUtils.toString(entity, "UTF-8");
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
        	httppost.releaseConnection();
        }  
        return result;
	}

}
