package com.demo.resolvers.argumentResolver;

import com.demo.converters.annotation.Json;
import com.demo.resolvers.strategy.ResolveArgumentStrategy;
import com.demo.resolvers.utils.ResolveArgumentStrategyUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by thinkpad on 2017/5/28.
 */
public class JsonMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Json.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String method = request.getMethod();
        ResolveArgumentStrategy resolveArgumentStrategy = ResolveArgumentStrategyUtils.getResolveArgumentStrategy(method);
        if(null == resolveArgumentStrategy){
            resolveArgumentStrategy = ResolveArgumentStrategyUtils.getResolveArgumentStrategy("DEFAULT");
        }
        return resolveArgumentStrategy.resolveArgumentValue(request);
    }
}
