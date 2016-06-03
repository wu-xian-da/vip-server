package com.jianfei.core.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
    
    private static final Log log = LogFactory.getLog(HttpClientUtils.class);
    
    //请求超时时间
    private static final Integer CONNECTION_TIMEOUT = 5000;
    //等待数据超时时间
    private static final Integer SO_TIMEOUT = 5000;
    //连接不够用的时候等待超时时间
    private static final Long CONN_MANAGER_TIMEOUT = 500L;
    //线程安全DefaultHttpClient
    private static HttpClient cachedHttpClient;

    static {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, PlainSocketFactory.getSocketFactory()));
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        //设置连接最大数
        cm.setMaxTotal(300);
        //设置每个Route的连接最大数
        cm.setDefaultMaxPerRoute(30);
        
        cachedHttpClient = new DefaultHttpClient(cm);
        cachedHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
        cachedHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
        cachedHttpClient.getParams().setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
    }
    
    //参数Encode
    public static String paramsEncoded(List<NameValuePair> params){
        return URLEncodedUtils.format(params, Consts.UTF_8);
    }
    
    //Post
    public static String post(String url, List<NameValuePair> params) {
        String result = null;
        HttpPost post = new HttpPost(url);
        HttpEntity entityRos = null;
        try {
            //设置参数
            post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            //发送请求
            HttpResponse httpResponse = cachedHttpClient.execute(post);
            //获取返回数据
            entityRos = httpResponse.getEntity();
            result = EntityUtils.toString(entityRos, Consts.UTF_8);
        } catch (UnsupportedEncodingException e) {
            log.error("参数异常******", e);
        } catch (ClientProtocolException e) {
            log.error("ClientProtocol异常******", e);
        } catch (IOException e) {
            log.error("IO异常******", e);
        } catch (Exception e) {
            log.error("异常******", e);
        } finally {
            EntityUtils.consumeQuietly(entityRos);
            post.releaseConnection();
        }
        return result;
    }

    //Get 请求
    public static String get(String url) {
        String result = null;
        HttpGet get = new HttpGet(url);
        HttpEntity entityRos = null;
        try {
            //发送请求
            HttpResponse httpResponse = cachedHttpClient.execute(get);
            //获取返回数据
            entityRos = httpResponse.getEntity();
            result = EntityUtils.toString(entityRos, Consts.UTF_8);
        } catch (UnsupportedEncodingException e) {
            log.error("参数异常******", e);
        } catch (ClientProtocolException e) {
            log.error("ClientProtocol异常******", e);
        } catch (IOException e) {
            log.error("IO异常******", e);
        } catch (Exception e) {
            log.error("异常******", e);
        } finally {
            EntityUtils.consumeQuietly(entityRos);
            get.releaseConnection();
        }
        return result;
    }
    
    //自定义get/post请求参数设置
    public static HttpClient getHttpClient(){
        return cachedHttpClient;
    }
    
}
