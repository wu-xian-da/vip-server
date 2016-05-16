
package com.jianfei.frame.spring.rest;

import com.jianfei.frame.Constants;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;

public class ServiceMethodRequestCondition extends AbstractRequestCondition<ServiceMethodRequestCondition> {

    private String method;

    private String version;

    public ServiceMethodRequestCondition() {
    }

    public ServiceMethodRequestCondition(String method, String version) {
        this.version = version;
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public String getVersion() {
        return version;
    }

    @Override
    protected Collection<String> getContent() {
        return Arrays.asList(method);
    }

    @Override
    protected String getToStringInfix() {
        return " && ";
    }

    @Override
    public ServiceMethodRequestCondition combine(ServiceMethodRequestCondition other) {
        return new ServiceMethodRequestCondition(other.getMethod(), other.getVersion());
    }

    @Override
    public ServiceMethodRequestCondition getMatchingCondition(HttpServletRequest request) {
        String _method = request.getParameter(Constants.SYS_PARAM_KEY_METHOD);
        String _version = request.getParameter(Constants.SYS_PARAM_KEY_VERSION);
        return new ServiceMethodRequestCondition(_method, _version);
    }

    @Override
    public int compareTo(ServiceMethodRequestCondition other, HttpServletRequest request) {
        String _method = request.getParameter(Constants.SYS_PARAM_KEY_METHOD);
        String _version = request.getParameter(Constants.SYS_PARAM_KEY_VERSION);

        if (new ServiceMethodRequestCondition(_method, _version).equals(other)) {
            return 0;
        }

        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ServiceMethodRequestCondition)) {
            return false;
        }
        ServiceMethodRequestCondition other = ((ServiceMethodRequestCondition) obj);

        if ((this.method != null && other.method != null) && !this.getMethod().equals(other.getMethod())) {
            return false;
        }

        if ((this.version != null && other.version != null) && !this.getVersion().equals(other.getVersion())) {
            return false;
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.format("{ method='%s', version='%s' }", method, version);
    }
}

