package com.jianfei.frame.spring.rest;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.jianfei.frame.Constants;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

/**
 * Author: yuwang@iflytek.com
 * Date: 2015/11/26 17:00
 */
public class ServiceMethodLookPath {

    /**
     * 分隔符
     */
    private final static String SPLITTER = "|";

    private String method;
    private String version;
    private RequestMethod httpMethod;

    /**
     *
     * @param request
     */
    public ServiceMethodLookPath(HttpServletRequest request) {
        this.method = request.getParameter(Constants.SYS_PARAM_KEY_METHOD);
        this.version = request.getParameter(Constants.SYS_PARAM_KEY_VERSION);
        this.httpMethod = RequestMethod.valueOf(request.getMethod());
    }

    /**
     * @param method
     * @param version
     * @param httpMethod
     */
    public ServiceMethodLookPath(String method, String version, RequestMethod httpMethod) {
        this.method = method;
        this.version = version;
        this.httpMethod = httpMethod;
    }

    /**
     * @param lookupPath 方法查找路径，样例： test_1.0_POST
     */
    public ServiceMethodLookPath(String lookupPath) {

        // TODO: 做入参校验
        Iterator<String> infos = Splitter.on(SPLITTER).split(lookupPath).iterator();
        this.method = infos.next();
        this.version = infos.next();
        this.httpMethod = RequestMethod.valueOf(infos.next());
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public RequestMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(RequestMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public String toString() {
        return Joiner.on(SPLITTER).join(method, version, httpMethod);
    }
}
