package com.github.zjor.ext.spring;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

public class TenantArgumentResolver implements HandlerMethodArgumentResolver {

    //TODO: resolve from JWT
    public final static String DEFAULT_TENANT = "anonymous";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        for (Annotation a: parameter.getParameterAnnotations()) {
            if (a instanceof Tenant) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return DEFAULT_TENANT;
    }
}
