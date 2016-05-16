
package com.jianfei.frame.web.filter;


import com.jianfei.frame.Constants;
import com.jianfei.frame.error.MainError;
import com.jianfei.frame.error.MainErrorType;
import com.jianfei.frame.error.MainErrors;
import com.jianfei.frame.error.support.ErrorRequestMessageConverter;
import com.jianfei.frame.spring.rest.ServiceMethodHandlerMapping;
import com.jianfei.frame.spring.rest.ServiceMethodInfo;
import com.jianfei.frame.spring.rest.ServiceMethodLookPath;
import com.jianfei.frame.spring.rest.ServiceMethodSupportInfo;
import com.jianfei.frame.support.MethodRequestInfo;
import com.jianfei.frame.support.ServiceRequestLogging;
import com.jianfei.frame.utils.EnvUtil;
import com.jianfei.frame.utils.RestContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

/**
 * 检测请求服务参数是否正确，测试：format、method、version、access_token、appkey
 * <p/>
 * 通过反射从RequestMappingHandlerMapping中获取RequestMappingInfo信息。
 * 服务请求时，通过RequestMappingInfo信息验证method和version是否正确。
 *
 * @author libinsong1204@gmail.com
 */
public class CheckOpenServiceFilter implements Filter, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckOpenServiceFilter.class);
    private static final Logger traceLogger = LoggerFactory.getLogger("http.request");

    private ErrorRequestMessageConverter messageConverter;
    private ServiceMethodHandlerMapping handlerMapping;

    private ServiceRequestLogging requestLogging = new ServiceRequestLogging();

    private boolean requireAppKey = false;

    private JdbcTemplate jdbcTemplate;

    public CheckOpenServiceFilter() {
    }

    public CheckOpenServiceFilter(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("通过反射从RequestMappingHandlerMapping中获取RequestMappingInfo信息");

        Field urlMapField = AbstractHandlerMethodMapping.class.getDeclaredField("urlMap");
        urlMapField.setAccessible(true);
        LinkedMultiValueMap<String, ServiceMethodInfo> urlMap =
                (LinkedMultiValueMap<String, ServiceMethodInfo>) urlMapField.get(handlerMapping);

        for (Entry<String, List<ServiceMethodInfo>> entry : urlMap.entrySet()) {
            ServiceMethodSupportInfo.put(new ServiceMethodLookPath(entry.getKey()));
        }

        //如果没有启动oauth认证，可以不需要appkey参数
        if (EnvUtil.oauthEnabled()) {
            requireAppKey = true;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        MainError mainError = null;
        Locale locale = getLocale(request);
        String format = request.getParameter(Constants.SYS_PARAM_KEY_FORMAT);
        if (!StringUtils.hasText(format)) {
            format = Constants.DATA_FORMAT_XML;
        }

        request.setAttribute(Constants.SYS_PARAM_KEY_LOCALE, locale);
        request.setAttribute(Constants.SYS_PARAM_KEY_FORMAT, format);

        String appkey = request.getParameter(Constants.SYS_PARAM_KEY_APPKEY);
        if (!StringUtils.hasText(appkey)) {
            if (requireAppKey)
                mainError = MainErrors.getError(MainErrorType.MISSING_APPKEY, locale);
        } else {
            RestContextHolder.getContext().addParam(Constants.SYS_PARAM_KEY_APPKEY, appkey);
        }

        String httpMethod = null;
        //只支持GET, POST
        if (mainError == null) {
            httpMethod = httpServletRequest.getMethod();
            if (!Constants.HTTP_METHOD_GET.equals(httpMethod) &&
                    !Constants.HTTP_METHOD_POST.equals(httpMethod)) {
                mainError = MainErrors.getError(MainErrorType.HTTP_ACTION_NOT_ALLOWED, locale);
            }
        }

        //判断service method、version方法是否存在
        String method = request.getParameter(Constants.SYS_PARAM_KEY_METHOD);
        if (mainError == null) {
            if (!StringUtils.hasText(method)) {
                //缺少method参数
                mainError = MainErrors.getError(MainErrorType.MISSING_METHOD, locale);
            } else {
                if (ServiceMethodSupportInfo.supportMethod(method)) {
                    String version = request.getParameter(Constants.SYS_PARAM_KEY_VERSION);

                    if (!StringUtils.hasText(version)) {
                        //版本号为空
                        mainError = MainErrors.getError(MainErrorType.MISSING_VERSION, locale);
                    } else if (!ServiceMethodSupportInfo.supportVersion(method, version)) {
                        // 对应版本号不存在
                        mainError = MainErrors.getError(MainErrorType.UNSUPPORTED_VERSION, locale);
                    } else if (!ServiceMethodSupportInfo.supportHttpMethod(method, version, httpMethod)) {
                        // 不支持的HTTP 方法
                        mainError = MainErrors.getError(MainErrorType.UNSUPPORTED_HTTP_METHOD, locale, ServiceMethodSupportInfo.getSupportHttpMethod(method, version));
                    }
                } else {
                    //method不存在
                    mainError = MainErrors.getError(MainErrorType.INVALID_METHOD, locale);
                }
            }
        }

        if (!EnvUtil.isRunningInEWP()) {
            if (mainError == null) {
                mainError = checkServiceMethodAuth(method, appkey, locale);
            }

            //判断access_token是否为空
            if (mainError == null) {
                if (EnvUtil.oauthEnabled()) {
                    String accessToken = request.getParameter(Constants.SYS_PARAM_KEY_ACCESS_TOKEN);
                    if (!checkHeaderToken(httpServletRequest) && !StringUtils.hasText(accessToken)) {
                        mainError = MainErrors.getError(MainErrorType.MISSING_ACCESS_TOKEN, locale);
                    }
                }
            }
        }

        // jsonp
        String callback = httpServletRequest.getParameter(Constants.SYS_PARAM_KEY_CALLBACK);
        if (StringUtils.hasText(callback)) {
            RestContextHolder.getContext().addParam(Constants.SYS_PARAM_KEY_CALLBACK, callback);
        }

        if (mainError != null) {
            request.setAttribute(Constants.MAIN_ERROR_CODE, mainError.getCode());
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            messageConverter.convertData(httpServletRequest, httpServletResponse, mainError);
        } else {
            MethodRequestInfo requestInfo = MethodRequestInfo.getMethodRequestInfo(httpServletRequest);
            RestContextHolder.getContext().setMethodRequestInfo(requestInfo);
            traceLogger.info(String.format("START-> consumer: %s, appname: %s, params: %s", requestInfo.getClientIp(), requestInfo.getAppkey(), requestInfo.getClientType() + " - " + requestInfo.getClientVersion()));
            chain.doFilter(request, response);
            traceLogger.info(String.format("END-> elapsed: %d, result: %s", request.getAttribute(ServiceMetricsFilter.SERVICE_EXEC_TIME), requestInfo.getResponseStatus()));
        }

        requestLogging.recordLog(httpServletRequest, httpServletResponse);
        RestContextHolder.clearContext();
    }

    private Locale getLocale(ServletRequest request) {
        String localePart = request.getParameter(Constants.SYS_PARAM_KEY_LOCALE);
        if (!StringUtils.hasText(localePart))
            localePart = "zh_CN";

        Locale locale = StringUtils.parseLocaleString(localePart);
        return locale;
    }

    private boolean checkHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检测服务是否被禁用
     */
    private static final String sql = "SELECT a.security FROM oauth_service_security a WHERE a.client_id=? AND a.service_name=?";

    private MainError checkServiceMethodAuth(String method, String appkey, Locale locale) {
        if (EnvUtil.isServiceMethodAuth()) {
            try {
                int status = jdbcTemplate.queryForObject(sql, Integer.class, appkey, method);
                if (status == 1)
                    return MainErrors.getError(MainErrorType.METHOD_DISABLED, locale);
            } catch (EmptyResultDataAccessException e) {
                //
            }
        }
        return null;
    }

    @Override
    public void destroy() {

    }

    public ServiceMethodHandlerMapping getHandlerMapping() {
        return handlerMapping;
    }

    public void setHandlerMapping(ServiceMethodHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public ErrorRequestMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(ErrorRequestMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @SuppressWarnings("serial")
    public static class SimpleAbstractAuthenticationToken extends AbstractAuthenticationToken {
        private String appkey;

        public SimpleAbstractAuthenticationToken(String appkey) {
            super(null);
            this.appkey = appkey;
        }

        public SimpleAbstractAuthenticationToken(
                Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
        }

        @Override
        public Object getCredentials() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getPrincipal() {
            return appkey;
        }

    }
}
