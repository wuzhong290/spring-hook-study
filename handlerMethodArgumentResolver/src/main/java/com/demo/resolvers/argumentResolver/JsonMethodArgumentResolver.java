package com.demo.resolvers.argumentResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.demo.resolvers.annotation.Json;
import com.demo.resolvers.strategy.ResolveArgumentStrategy;
import com.demo.resolvers.utils.ResolveArgumentStrategyUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(JsonMethodArgumentResolver.class);
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
        String value = resolveArgumentStrategy.resolveArgumentValue(request);
        LOG.debug("Read request method is {},value is {}",method, value);
        return JSON.parseObject(value, parameter.getParameterType(), Feature.AllowSingleQuotes);
    }
}
