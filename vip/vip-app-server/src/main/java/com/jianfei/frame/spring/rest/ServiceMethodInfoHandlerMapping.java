
package com.jianfei.frame.spring.rest;

import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

abstract public class ServiceMethodInfoHandlerMapping extends AbstractHandlerMethodMapping<ServiceMethodInfo> {

    /**
     * Get the URL path patterns associated with this {@link RequestMappingInfo}.
     */
    @Override
    protected Set<String> getMappingPathPatterns(ServiceMethodInfo info) {
        HashSet<String> set = new HashSet<String>();

        ServiceMethodRequestCondition smrc = info.getServiceMethodCondition();
        Set<RequestMethod> methods = info.getMethodsCondition().getMethods();

        for (RequestMethod method : methods) {
            set.add(new ServiceMethodLookPath(smrc.getMethod(), smrc.getVersion(), method).toString());
        }
        return set;
    }

    /**
     * Check if the given RequestMappingInfo matches the current request and
     * return a (potentially new) instance with conditions that match the
     * current request -- for example with a subset of URL patterns.
     *
     * @return an info in case of a match; or {@code null} otherwise.
     */
    @Override
    protected ServiceMethodInfo getMatchingMapping(ServiceMethodInfo info, HttpServletRequest request) {
        return info.getMatchingCondition(request);
    }

    /**
     * Provide a Comparator to sort RequestMappingInfos matched to a request.
     */
    @Override
    protected Comparator<ServiceMethodInfo> getMappingComparator(final HttpServletRequest request) {
        return new Comparator<ServiceMethodInfo>() {
            @Override
            public int compare(ServiceMethodInfo info1, ServiceMethodInfo info2) {
                return info1.compareTo(info2, request);
            }
        };
    }

    /**
     * Expose URI template variables, matrix variables, and producible media types in the request.
     *
     * @see HandlerMapping#URI_TEMPLATE_VARIABLES_ATTRIBUTE
     * @see HandlerMapping#MATRIX_VARIABLES_ATTRIBUTE
     * @see HandlerMapping#PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE
     */
    @Override
    protected void handleMatch(ServiceMethodInfo info, String lookupPath, HttpServletRequest request) {
        super.handleMatch(info, lookupPath, request);
        request.setAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE, lookupPath);
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, Collections.emptyMap());
    }

    /**
     * Iterate all RequestMappingInfos once again, look if any match by URL at
     * least and raise exceptions accordingly.
     *
     * @throws HttpRequestMethodNotSupportedException if there are matches by URL
     *                                                but not by HTTP method
     * @throws HttpMediaTypeNotAcceptableException    if there are matches by URL
     *                                                but not by consumable/producible media types
     */
    @Override
    protected HandlerMethod handleNoMatch(Set<ServiceMethodInfo> requestMappingInfos,
                                          String lookupPath, HttpServletRequest request) throws ServletException {

        Set<String> allowedMethods = new LinkedHashSet<String>(4);

        Set<ServiceMethodInfo> patternMatches = new HashSet<ServiceMethodInfo>();
        Set<ServiceMethodInfo> patternAndMethodMatches = new HashSet<ServiceMethodInfo>();

        for (ServiceMethodInfo info : requestMappingInfos) {
            if (info.getServiceMethodCondition().getMatchingCondition(request) != null) {
                patternMatches.add(info);
                if (info.getMethodsCondition().getMatchingCondition(request) != null) {
                    patternAndMethodMatches.add(info);
                } else {
                    for (RequestMethod method : info.getMethodsCondition().getMethods()) {
                        allowedMethods.add(method.name());
                    }
                }
            }
        }

        if (patternMatches.isEmpty()) {
            return null;
        } else if (patternAndMethodMatches.isEmpty() && !allowedMethods.isEmpty()) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(), allowedMethods);
        } else {
            return null;
        }
    }

}
