
package com.jianfei.frame.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 判断指定appkey不需要认证授权
 * <p/>
 * Create on @2014年8月7日 @下午7:47:34
 *
 * @author libinsong1204@gmail.com
 */
public class DelegatingFilterProxyExt extends DelegatingFilterProxy {
    private static final Logger logger = LoggerFactory.getLogger(DelegatingFilterProxyExt.class);

    private List<String> excludeAppKeys;

    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        String appkey = System.getProperty("excludes.appkey");
        if (StringUtils.hasText(appkey)) {
            excludeAppKeys = Arrays.asList(appkey.split(","));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws ServletException, IOException {
        String appkey = request.getParameter("appkey");
        if (excludeAppKeys != null && excludeAppKeys.contains(appkey)) {
            logger.debug("应用(appkey=" + appkey + ")不需要oauth安全认证。");
            filterChain.doFilter(request, response);
        } else {
            super.doFilter(request, response, filterChain);
        }
    }
}
