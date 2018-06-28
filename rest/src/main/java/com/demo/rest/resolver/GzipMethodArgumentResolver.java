package com.demo.rest.resolver;

import com.demo.rest.annotation.Gzip;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/**
 * Created by thinkpad on 2017/5/28.
 */
public class GzipMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger LOG = LoggerFactory.getLogger(GzipMethodArgumentResolver.class);
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Gzip annotation = parameter.getParameterAnnotation(Gzip.class);
        LOG.info(parameter.getMethod().getName());
        return ObjectUtils.allNotNull(annotation);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        GZIPInputStream gzipInputStream = new GZIPInputStream(request.getInputStream());
        String str = IOUtils.toString(gzipInputStream, StandardCharsets.UTF_8.name());
        LOG.info(str);
        return str;
    }
}
