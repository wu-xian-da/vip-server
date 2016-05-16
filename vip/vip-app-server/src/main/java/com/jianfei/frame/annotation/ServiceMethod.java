
package com.jianfei.frame.annotation;

import com.jianfei.frame.support.GrantType;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ServiceMethod {

    /**
     * 方法名.
     */
    String value() default "";

    /**
     * 版本号.
     */
    String version() default "";

    /**
     * 方法授权类别.
     */
    GrantType[] grantType() default {GrantType.AUTH_CODE, GrantType.IMPLICIT,
            GrantType.REFRESH_TOKEN, GrantType.CLIENT, GrantType.PASSWORD};

    /**
     * 允许的HTTP Method.
     */
    RequestMethod[] method() default {RequestMethod.GET, RequestMethod.POST};
}
