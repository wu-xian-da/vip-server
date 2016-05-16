
package com.jianfei.frame.spring;


import com.jianfei.frame.Constants;
import com.jianfei.frame.error.support.ReturnValueConverterException;
import com.jianfei.frame.utils.EnvUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author libinsong1204@gmail.com
 */
public class RequestResponseBodyMethodProcessorExt extends RequestResponseBodyMethodProcessor {
    private final static MediaType MEDIA_TYPE_XML = MediaType.valueOf("application/xml;charset=UTF-8");

    private final static MediaType MEDIA_TYPE_JSON = MediaType.valueOf("application/json;charset=UTF-8");

    public RequestResponseBodyMethodProcessorExt(
            List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
    }

    @Override
    protected <T> void writeWithMessageConverters(T returnValue, MethodParameter returnType,
                                                  ServletServerHttpRequest inputMessage, ServletServerHttpResponse outputMessage)
            throws IOException, HttpMediaTypeNotAcceptableException {
        Class<?> returnValueClass = returnValue.getClass();
        HttpServletRequest servletRequest = inputMessage.getServletRequest();
        String format = servletRequest.getParameter(Constants.SYS_PARAM_KEY_FORMAT);

        MediaType contentType = MEDIA_TYPE_XML;

        if (Constants.DATA_FORMAT_JSON.equals(format)) {
            contentType = MEDIA_TYPE_JSON;
        }

        if (ClassUtils.isPrimitiveOrWrapper(returnValueClass)) {
            if (Constants.DATA_FORMAT_JSON.equals(format)) {
                String result = "{\"return\":\"" + returnValue + "\"}";
                write(result, contentType, outputMessage);
            } else {
                String result = "<return>" + returnValue + "</return>";
                write(result, contentType, outputMessage);
            }

        } else {

            boolean findConverter = false;
            for (HttpMessageConverter<?> messageConverter : this.messageConverters) {
                if (messageConverter.canWrite(returnValueClass, contentType)) {
                    findConverter = true;
                    ((HttpMessageConverter<T>) messageConverter).write(returnValue, contentType, outputMessage);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Written [" + returnValue + "] as \"" + contentType + "\" using [" + messageConverter + "]");
                    }
                    break;
                }
            }

            if (!findConverter) {
                throw new ReturnValueConverterException(returnValueClass);
            }
        }
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
            throws IOException, HttpMediaTypeNotAcceptableException {

        if (EnvUtil.isRunningInEWP()) {
            super.handleReturnValue(new DefaultReturnModel(returnValue), returnType, mavContainer, webRequest);
        } else {
            super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        }
    }

    private Charset charset = Charset.forName("UTF-8");

    private void write(final String content, MediaType contentType, ServletServerHttpResponse outputMessage)
            throws IOException, HttpMessageNotWritableException {
        final HttpHeaders headers = outputMessage.getHeaders();
        headers.setContentType(contentType);
        if (headers.getContentLength() == -1) {
            Long contentLength = getContentLength(content, headers.getContentType());
            if (contentLength != null) {
                headers.setContentLength(contentLength);
            }
        }

        StreamUtils.copy(content, charset, outputMessage.getBody());
        outputMessage.getBody().flush();
    }

    private Long getContentLength(String s, MediaType contentType) {
        try {
            return (long) s.getBytes(charset.name()).length;
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
