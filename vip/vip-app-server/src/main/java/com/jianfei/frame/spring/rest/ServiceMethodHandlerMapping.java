
package com.jianfei.frame.spring.rest;

import com.jianfei.frame.annotation.ServiceMethod;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class ServiceMethodHandlerMapping extends ServiceMethodInfoHandlerMapping {

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return ((AnnotationUtils.findAnnotation(beanType, Controller.class) != null) ||
                (AnnotationUtils.findAnnotation(beanType, ServiceMethod.class) != null));
    }

    @Override
    protected ServiceMethodInfo getMappingForMethod(Method method,
                                                    Class<?> handlerType) {
        ServiceMethodInfo info = null;
        ServiceMethod methodAnnotation = AnnotationUtils.findAnnotation(
                method, ServiceMethod.class);
        if (methodAnnotation != null) {
            info = createServiceMethodInfo(methodAnnotation);
            ServiceMethod typeAnnotation = AnnotationUtils.findAnnotation(
                    handlerType, ServiceMethod.class);
            if (typeAnnotation != null) {
                info = createServiceMethodInfo(typeAnnotation).combine(info);
            }

        }
        return info;
    }

    protected ServiceMethodInfo createServiceMethodInfo(ServiceMethod annotation) {
        return new ServiceMethodInfo(new ServiceMethodRequestCondition(annotation.value(), annotation.version()),
                new RequestMethodsRequestCondition(annotation.method()));
    }

    @Override
    protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {

        ServiceMethodLookPath lookPath = new ServiceMethodLookPath(request);
        String lookupPath = lookPath.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("Looking up handler method for path " + lookupPath);
        }
        HandlerMethod handlerMethod = lookupHandlerMethod(lookupPath, request);
        if (logger.isDebugEnabled()) {
            if (handlerMethod != null) {
                logger.debug("Returning handler method [" + handlerMethod + "]");
            } else {
                logger.debug("Did not find handler method for [" + lookupPath + "]");
            }
        }
        return (handlerMethod != null ? handlerMethod.createWithResolvedBean() : null);
    }
}
