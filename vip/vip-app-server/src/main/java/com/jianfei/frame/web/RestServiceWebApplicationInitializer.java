
package com.jianfei.frame.web;


import com.jianfei.frame.spring.DelegatingFilterProxyExt;
import com.jianfei.frame.spring.ProfileApplicationContextInitializer;
import com.jianfei.frame.utils.EnvUtil;
import com.jianfei.frame.web.listener.LogBackLoadConfigureListener;
import com.jianfei.frame.web.servlet.PrintProjectVersionServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author libinsong1204@gmail.com
 */
public class RestServiceWebApplicationInitializer implements
        WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(RestServiceWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {

        // 加载Spring配置文件
        servletContext.setInitParameter("contextConfigLocation", "classpath*:META-INF/spring/*-context.xml");
        servletContext.setInitParameter("contextInitializerClasses", ProfileApplicationContextInitializer.class.getName());
        servletContext.addListener(new LogBackLoadConfigureListener());
        servletContext.addListener(new ContextLoaderListener());

        // 添加过滤器
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
        EnumSet<DispatcherType> characterEncodingFilterDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(characterEncodingFilterDispatcherTypes, true, "/*");

        // CORS攻击防御filter
        FilterRegistration.Dynamic CORSFilter = servletContext.addFilter("CORSFilter", new DelegatingFilterProxy());
        EnumSet<DispatcherType> CORSFilterDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        CORSFilter.addMappingForUrlPatterns(CORSFilterDispatcherTypes, true, "/api");

        // 开放服务filter
        FilterRegistration.Dynamic openServiceFilter = servletContext.addFilter("openServiceFilter", new DelegatingFilterProxy());
        EnumSet<DispatcherType> openServiceFilterDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        openServiceFilter.addMappingForUrlPatterns(openServiceFilterDispatcherTypes, true, "/api");

        // EWP中的授权过滤器
        if(EnvUtil.isRunningInEWP()) {
            // ewp authorization
            FilterRegistration.Dynamic ewpAuthorizationFilter = servletContext.addFilter("ewpAuthorizationFilter", new DelegatingFilterProxy());
            EnumSet<DispatcherType> authorizationFilterChainDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
            ewpAuthorizationFilter.addMappingForUrlPatterns(authorizationFilterChainDispatcherTypes, true, "/api");

            // check authorization result
            FilterRegistration.Dynamic authorizationResultFilter = servletContext.addFilter("ewpAuthorizationResultFilter", new DelegatingFilterProxy());
            EnumSet<DispatcherType> authorizationResultFilterDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
            authorizationResultFilter.addMappingForUrlPatterns(authorizationResultFilterDispatcherTypes, true, "/api");
        }

        // 服务调用统计filter
        if (EnvUtil.jdbcEnabled()) {
            FilterRegistration.Dynamic serviceMetricsFilter = servletContext.addFilter("serviceMetricsFilter", new DelegatingFilterProxy());
            EnumSet<DispatcherType> serviceMetricsFilterDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
            serviceMetricsFilter.addMappingForUrlPatterns(serviceMetricsFilterDispatcherTypes, true, "/api");
        }

        // OAuth鉴权filter
        if ( EnvUtil.oauthEnabled()) {
            FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxyExt());
            EnumSet<DispatcherType> springSecurityFilterChainDispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
            springSecurityFilterChain.addMappingForUrlPatterns(springSecurityFilterChainDispatcherTypes, true, "/api");
        } else {
            logger.info("没有启动oauth2认证，需要启动，请在META-INF/res/profile.properties文件中添加oauth2 profile");
        }

        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("rest", new DispatcherServlet());
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
        dispatcherServlet.setInitParameter("contextConfigLocation", "org.spring.rest");
        dispatcherServlet.setMultipartConfig(getMultiPartConfig());
        dispatcherServlet.addMapping("/api");

        ServletRegistration.Dynamic printProjectVersionServlet = servletContext.addServlet("printProjectVersionServlet", new PrintProjectVersionServlet());
        printProjectVersionServlet.setLoadOnStartup(Integer.MAX_VALUE);
    }

    private MultipartConfigElement getMultiPartConfig() {
        String location = System.getProperty("java.io.tmpdir");

        long maxFileSize = -1;
        long maxRequestSize = -1;
        int fileSizeThreshold = -1;
        return new MultipartConfigElement(
                location,
                maxFileSize,
                maxRequestSize,
                fileSizeThreshold
        );
    }
}
