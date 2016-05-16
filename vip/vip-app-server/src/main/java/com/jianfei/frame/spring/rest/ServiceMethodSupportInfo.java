package com.jianfei.frame.spring.rest;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Author: yuwang@iflytek.com
 * Date: 2015/11/26 16:59
 */
public class ServiceMethodSupportInfo {

    private final static String SPLITTER = "|";

    private final static Set<String> METHOD = Sets.newHashSet();
    private final static Set<String> VERSION = Sets.newHashSet();
    private final static Set<String> HTTP_METHOD = Sets.newHashSet();
    private final static Multimap<String, String> HTTP_METHOD_SUPPORT =  ArrayListMultimap.create();

    public static void put(ServiceMethodLookPath lookPath) {
        METHOD.add(lookPath.getMethod());
        String versionKey = getVersionSearchKey(lookPath.getMethod(), lookPath.getVersion());
        VERSION.add(versionKey);
        HTTP_METHOD.add(getHttpMethodSearchKey(lookPath.getMethod(), lookPath.getVersion(), lookPath.getHttpMethod().toString()));
        HTTP_METHOD_SUPPORT.put(versionKey, lookPath.getHttpMethod().toString());
    }

    private static String getVersionSearchKey(String method, String version) {
        return Joiner.on(SPLITTER).join(method,version);
    }

    private static String getHttpMethodSearchKey(String method, String version, String httpMethod) {
        return Joiner.on(SPLITTER).join(method,version, httpMethod);
    }

    /**
     * 判断方法是否支持
     *
     * @param method 方法名
     * @return true: 支持， false：不支持
     */
    public static boolean supportMethod(String method) {
        return METHOD.contains(method);
    }

    /**
     * 判断版本是否支持
     *
     * @param method 方法名
     * @param version 版本
     * @return true: 支持， false：不支持
     */
    public static boolean supportVersion(String method, String version) {
        return VERSION.contains(getVersionSearchKey(method, version));
    }

    /**
     * 判断HTTP方法是否支持
     *
     * @param method 方法名
     * @param version 版本
     * @param httpMethod HTTP方法
     * @return true: 支持， false：不支持
     */
    public static boolean supportHttpMethod(String method, String version, String httpMethod) {
        return HTTP_METHOD.contains(getHttpMethodSearchKey(method, version, httpMethod));
    }

    /**
     *  获取方法支持的HTTP METHOD
     *
     * @param method
     * @param version
     * @return
     */
    public static String getSupportHttpMethod(String method, String version) {
        String versionKey = getVersionSearchKey(method, version);
        return Joiner.on("/").join(HTTP_METHOD_SUPPORT.get(versionKey));
    }

}
