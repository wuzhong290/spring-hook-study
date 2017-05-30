package com.demo.resolvers.argumentResolver;

import com.demo.converters.annotation.Json;
import com.demo.resolvers.strategy.DefaultArgumentStrategy;
import com.demo.resolvers.strategy.GetArgumentStrategy;
import com.demo.resolvers.strategy.ResolveArgumentStrategy;
import com.sun.javafx.collections.MappingChange;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/5/28.
 */
public class JsonMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final static Map<String, ResolveArgumentStrategy> STRATEGYS = new HashMap<>();

    static{
        ResolveArgumentStrategy getArgumentStrategy = new GetArgumentStrategy();
        ResolveArgumentStrategy defaultArgumentStrategy = new DefaultArgumentStrategy();
        STRATEGYS.put("GET", getArgumentStrategy);
        STRATEGYS.put("DELETE", getArgumentStrategy);
        STRATEGYS.put("POST", defaultArgumentStrategy);
        STRATEGYS.put("DEFAULT", defaultArgumentStrategy);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Json.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String method = request.getMethod();
        ResolveArgumentStrategy resolveArgumentStrategy = STRATEGYS.get(method);
        if(null == resolveArgumentStrategy){
            resolveArgumentStrategy = STRATEGYS.get("DEFAULT");
        }
        return resolveArgumentStrategy.resolveArgumentValue(request);
    }
}
