/**
 *
 */
package com.jianfei.frame.web.filter;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Create on @2014年8月5日 @下午1:38:28
 *
 * @author libinsong1204@gmail.com
 */
public class CORSFilter extends OncePerRequestFilter implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(CORSFilter.class);
    private String origins;
    private Set<String> allowedOrigins;
    private String originRegex;
    private Pattern allowedOriginRegex = null;

    @Autowired
    private Properties propertiesConfiguration;

    @Override
    protected void initFilterBean() throws ServletException {
        origins = propertiesConfiguration.getProperty("allowed.origins", null);
        originRegex = propertiesConfiguration.getProperty("allowed.origin.regex", null);
        allowedOrigins = null;
        allowedOriginRegex = null;
        if (StringUtils.isNotEmpty(origins)) {
            allowedOrigins = new HashSet<>(Arrays.asList(origins.split(";")));
            logger.info("allowedOrigins is: {}", allowedOrigins);
        }
        if (StringUtils.isNotEmpty(originRegex)) {
            try {
                allowedOriginRegex = Pattern.compile(originRegex, Pattern.CASE_INSENSITIVE);
                logger.info("allowed.origin.regex is: {}", originRegex);
            } catch (PatternSyntaxException ex) {
                logger.warn("invalid allowed.origin.regex: {}", originRegex);
            }
        }
/*
        try {
            PropertiesConfiguration config = PropertiesConfigurationFactoryBean.getPropertiesConfiguration();
            config.addConfigurationListener(new ConfigurationListener() {
                @Override
                public void configurationChanged(ConfigurationEvent event) {
                    if (event.getPropertyName().equals("allowed.origins")) {
                        origins = (String) event.getPropertyValue();
                        if (StringUtils.isEmpty(origins)) {
                            allowedOrigins = null;
                            logger.info("allowedOrigins changed, is null");
                        } else {
                            allowedOrigins = new HashSet<>(Arrays.asList(origins.split(";")));
                            logger.info("allowedOrigins changed, is: {}", allowedOrigins);
                        }
                    } else if (event.getPropertyName().equals("allowed.origin.regex")) {
                        originRegex = (String) event.getPropertyValue();
                        if (StringUtils.isEmpty(originRegex)) {
                            allowedOriginRegex = null;
                            logger.info("allowed.origin.regex changed, is null");
                        } else {
                            try {
                                allowedOriginRegex = Pattern.compile(originRegex, Pattern.CASE_INSENSITIVE);
                                logger.info("allowed.origin.regex changed, is: {}", originRegex);
                            } catch (PatternSyntaxException ex) {
                                logger.warn("invalid allowed.origin.regex: {}", originRegex);
                                allowedOriginRegex = null;
                            }
                        }
                    }
                }
            });
        } catch (ConfigurationRuntimeException ex) {
        }*/
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String origin = request.getHeader("Origin");
        if (origin != null && (
                (allowedOrigins != null && allowedOrigins.contains(origin)) ||
                (allowedOriginRegex != null && allowedOriginRegex.matcher(origin).matches()))) {
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("Access-Control-Allow-Methods", "GET, POST");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        }

        filterChain.doFilter(request, response);
    }

    public String getOrigins() {
        return origins;
    }

    public void setOrigins(String origins) {
        this.origins = origins;
    }

    public String getOriginRegex() {
        return originRegex;
    }

    public void setOriginRegex(String originRegex) {
        this.originRegex = originRegex;
    }
}
