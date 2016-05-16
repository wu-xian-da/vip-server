
package com.jianfei.frame.web.filter;


import com.jianfei.frame.Constants;
import com.jianfei.frame.web.filter.stat.ServiceAppStat;
import com.jianfei.frame.web.filter.stat.ServiceMethodStat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author libinsong1204@gmail.com
 */
public class ServiceMetricsFilter extends GenericFilterBean {
    public static final String SERVICE_EXEC_TIME = ServiceMetricsFilter.class.getSimpleName() + "_exec_time";

    private ServiceAppStat serviceAppStat;

    private JdbcTemplate jdbcTemplate;

    public ServiceMetricsFilter(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void initFilterBean() throws ServletException {
        if (serviceAppStat == null) {
            serviceAppStat = new ServiceAppStat(jdbcTemplate);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        //HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        StatHttpServletResponseWrapper responseWrapper = new StatHttpServletResponseWrapper(httpResponse);

        final String method = request.getParameter(Constants.SYS_PARAM_KEY_METHOD);
        final String appkey = request.getParameter(Constants.SYS_PARAM_KEY_APPKEY);
        long startNano = System.nanoTime();
        long startMillis = System.currentTimeMillis();

        serviceAppStat.beforeInvoke();

        ServiceMethodStat methodStat = serviceAppStat.getMethodStat(appkey, method);
        if (methodStat == null) {
            methodStat = serviceAppStat.getMethodStat(appkey, method);
        }

        if (methodStat != null) {
            methodStat.beforeInvoke(startMillis);
        }

        Throwable error = null;
        try {
            chain.doFilter(request, responseWrapper);
        } catch (IOException e) {
            error = e;
            throw e;
        } catch (ServletException e) {
            error = e;
            throw e;
        } catch (RuntimeException e) {
            error = e;
            throw e;
        } catch (Error e) {
            error = e;
            throw e;
        } finally {
            long endNano = System.nanoTime();
            long nanos = endNano - startNano;
            serviceAppStat.afterInvoke(error, nanos);

            if (methodStat != null) {
                methodStat.afterInvoke(error, nanos);
                request.setAttribute(SERVICE_EXEC_TIME, nanos / (1000 * 1000));
            }
        }
    }

    @Override
    public void destroy() {
        serviceAppStat.destroy();
    }

    public final static class StatHttpServletResponseWrapper extends HttpServletResponseWrapper implements HttpServletResponse {

        private int status;

        public StatHttpServletResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        public void setStatus(int statusCode) {
            super.setStatus(statusCode);
            this.status = statusCode;
        }

        @SuppressWarnings("deprecation")
        public void setStatus(int statusCode, String statusMessage) {
            super.setStatus(statusCode, statusMessage);
            this.status = statusCode;
        }

        public void sendError(int statusCode, String statusMessage) throws IOException {
            super.sendError(statusCode, statusMessage);
            this.status = statusCode;
        }

        public void sendError(int statusCode) throws IOException {
            super.sendError(statusCode);
            this.status = statusCode;
        }

        public int getStatus() {
            return status;
        }
    }

}
