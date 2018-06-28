package com.demo.rest.resolver;

import com.demo.rest.annotation.Gzip;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

public class GzipMethodReturnValueHandler  implements HandlerMethodReturnValueHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GzipMethodArgumentResolver.class);
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        Gzip annotation = returnType.getParameterAnnotation(Gzip.class);
        LOG.info(returnType.getMethod().getName());
        return ObjectUtils.allNotNull(annotation);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputMessage.getBody());
        IOUtils.write((String) returnValue, gzipOutputStream, StandardCharsets.UTF_8.name());
    }

    protected ServletServerHttpResponse createOutputMessage(NativeWebRequest webRequest) {
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        return new ServletServerHttpResponse(response);
    }
}
