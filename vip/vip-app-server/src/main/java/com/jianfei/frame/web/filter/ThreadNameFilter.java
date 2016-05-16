package com.jianfei.frame.web.filter;


import com.jianfei.frame.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 修改请求线程名称 
 * 
 * @author libinsong1204@gmail.com
 */
public class ThreadNameFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { /* unused */ }

    @Override
    public void destroy() { /* unused */ }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final Thread current = Thread.currentThread();
        final String oldName = current.getName();
        try {
            current.setName(formatName(req, oldName));
            chain.doFilter(request, response);
        } finally {
            current.setName(oldName);
        }
    }

    private static String formatName(HttpServletRequest req, String oldName) {
    	final String method = req.getParameter(Constants.SYS_PARAM_KEY_METHOD);
        return oldName + "-" + req.getMethod() + "-" + method;
    }
}
