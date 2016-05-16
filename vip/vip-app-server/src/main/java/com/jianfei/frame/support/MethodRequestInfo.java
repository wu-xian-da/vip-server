package com.jianfei.frame.support;

import com.alibaba.fastjson.JSON;
import com.jianfei.frame.Constants;
import com.jianfei.frame.utils.ServiceUtil;
import com.jianfei.frame.web.filter.ServiceMetricsFilter;


import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Author: yuwang@iflytek.com
 * Date: 2015/10/13 15:28
 */
public class MethodRequestInfo {

    private String clientIp;
    private String locale;
    private String format;
    private String appkey;
    private String httpMethod;
    private String serviceMethod;
    private String serviceVersion;
    private int responseStatus;

    /**
     */
    private Long requestTimeMillis;
    private String mainErrorCode;
    private String clientType;
    private String clientVersion;

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Long getRequestTimeMillis() {
        return requestTimeMillis;
    }

    public void setRequestTimeMillis(Long requestTimeMillis) {
        this.requestTimeMillis = requestTimeMillis;
    }

    public String getMainErrorCode() {
        return mainErrorCode;
    }

    public void setMainErrorCode(String mainErrorCode) {
        this.mainErrorCode = mainErrorCode;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public static MethodRequestInfo getMethodRequestInfo(HttpServletRequest request) {
        String clientIp = ServiceUtil.getRemoteAddr(request);
        String locale = ((Locale) request.getAttribute(Constants.SYS_PARAM_KEY_LOCALE)).getDisplayName();
        String format = (String) request.getAttribute(Constants.SYS_PARAM_KEY_FORMAT);
        String appkey = request.getParameter(Constants.SYS_PARAM_KEY_APPKEY);
        String httpMethod = request.getMethod();
        String serviceMethod = request.getParameter(Constants.SYS_PARAM_KEY_METHOD);
        String serviceVersion = request.getParameter(Constants.SYS_PARAM_KEY_VERSION);
        String clientType = request.getParameter(Constants.SYS_PARAM_KEY_CLIENT_TYPE);
        String clientVersion = request.getParameter(Constants.SYS_PARAM_KEY_CLIENT_VERSION);
        Long requestTimeMillis = (Long) request.getAttribute(ServiceMetricsFilter.SERVICE_EXEC_TIME);
        String mainErrorCode = (String) request.getAttribute(Constants.MAIN_ERROR_CODE);

        MethodRequestInfo methodRequestInfo = new MethodRequestInfo();
        methodRequestInfo.setClientIp(clientIp);
        methodRequestInfo.setLocale(locale);
        methodRequestInfo.setFormat(format);
        methodRequestInfo.setAppkey(appkey);
        methodRequestInfo.setHttpMethod(httpMethod);
        methodRequestInfo.setServiceMethod(serviceMethod);
        methodRequestInfo.setServiceVersion(serviceVersion);
        methodRequestInfo.setClientType(clientType);
        methodRequestInfo.setClientVersion(clientVersion);
        methodRequestInfo.setRequestTimeMillis(requestTimeMillis);
        methodRequestInfo.setMainErrorCode(mainErrorCode);

        return methodRequestInfo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
