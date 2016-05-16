
package com.jianfei.frame.support;


import com.jianfei.frame.Constants;
import com.jianfei.frame.utils.RestContextHolder;
import com.jianfei.frame.utils.ServiceUtil;
import com.jianfei.frame.web.filter.ServiceMetricsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * 服务请求日志记录
 *
 * @author libinsong1204@gmail.com
 */
public class ServiceRequestLogging {

    private static final Logger LOGGER = LoggerFactory.getLogger("http.request");

    /**
     * @param request
     * @param response
     */
    public void recordLog(HttpServletRequest request, HttpServletResponse response) {
        String clientIp = ServiceUtil.getRemoteAddr(request);
        String locale = ((Locale) request.getAttribute(Constants.SYS_PARAM_KEY_LOCALE)).getDisplayName();
        String format = (String) request.getAttribute(Constants.SYS_PARAM_KEY_FORMAT);
        String appkey = request.getParameter(Constants.SYS_PARAM_KEY_APPKEY);
        String httpMethod = request.getMethod();
        String serviceMethod = request.getParameter(Constants.SYS_PARAM_KEY_METHOD);
        String serviceVersion = request.getParameter(Constants.SYS_PARAM_KEY_VERSION);
        String clientType = request.getParameter(Constants.SYS_PARAM_KEY_CLIENT_TYPE);
        String clientVersion = request.getParameter(Constants.SYS_PARAM_KEY_VERSION);
        int responseStatus = response.getStatus();
        Long requestTimeMillis = (Long) request.getAttribute(ServiceMetricsFilter.SERVICE_EXEC_TIME);
        Integer mainErrorCode = (Integer)request.getAttribute(Constants.MAIN_ERROR_CODE);

        Map<String, String[]> paraMap = request.getParameterMap();
        RestContext context = RestContextHolder.getContext();
        LOGGER.debug("service request information : mainErrorCode={}, clientIp={}, httpMethod={}, locale={},"
                        + " appkey={}, serviceMethod={}, serviceVersion={}, format={}, ct={}, cver={}, responseStatus={}, requestTimeMillis={},"
                        + " callCycoreCount={}, callCycoreTime={},mainParameters={}",
                mainErrorCode, clientIp, httpMethod, locale, appkey, serviceMethod,
                serviceVersion, format, clientType, clientVersion, responseStatus, requestTimeMillis,
                context.getCallCycoreCount(), context.getCallCycoreTime(), paraMap);

        if (mainErrorCode != null) {
            LOGGER.warn("service request information : mainErrorCode={}, clientIp={}, httpMethod={}, locale={},"
                            + " appkey={}, serviceMethod={}, serviceVersion={}, format={}, ct={}, cver={}, responseStatus={}, requestTimeMillis={},"
                            + " callCycoreCount={}, callCycoreTime={}",
                    mainErrorCode, clientIp, httpMethod, locale, appkey, serviceMethod,
                    serviceVersion, format, clientType, clientVersion, responseStatus, requestTimeMillis,
                    context.getCallCycoreCount(), context.getCallCycoreTime());
        } else {
            LOGGER.info("service request information : clientIp={}, httpMethod={}, locale={},"
                            + " appkey={}, serviceMethod={}, serviceVersion={}, format={}, ct={}, cver={}, responseStatus={}, requestTimeMillis={}"
                            + " callCycoreCount={}, callCycoreTime={}",
                    clientIp, httpMethod, locale, appkey, serviceMethod,
                    serviceVersion, format, clientType, clientVersion, responseStatus, requestTimeMillis,
                    context.getCallCycoreCount(), context.getCallCycoreTime());
        }
    }

}
