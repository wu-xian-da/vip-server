

package com.jianfei.frame.spring;


import com.jianfei.frame.error.MainError;
import com.jianfei.frame.error.MainErrorType;
import com.jianfei.frame.error.MainErrors;
import com.jianfei.frame.error.support.ErrorRequestMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author libinsong1204@gmail.com
 */
public class OAuth2AuthenticationEntryPointExt extends OAuth2AuthenticationEntryPoint {
    private ErrorRequestMessageConverter messageConverter;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        if (authException.getCause() instanceof InvalidTokenException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Locale locale = (Locale) request.getAttribute("locale");
            MainError mainError = MainErrors.getError(MainErrorType.INVALID_ACCESS_TOKEN, locale);
            messageConverter.convertData(request, response, mainError);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            doHandle(request, response, authException);
        }
    }

    public ErrorRequestMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(ErrorRequestMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}
