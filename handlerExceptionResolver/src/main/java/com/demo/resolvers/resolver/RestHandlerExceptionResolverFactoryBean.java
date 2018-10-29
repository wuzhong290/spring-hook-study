package com.demo.resolvers.resolver;

import com.demo.resolvers.handlers.RestExceptionHandler;
import com.demo.resolvers.interpolators.MessageInterpolator;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.accept.ContentNegotiationManager;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Setter
public class RestHandlerExceptionResolverFactoryBean implements FactoryBean<RestHandlerExceptionResolver> {

    private ContentNegotiationManager contentNegotiationManager;

    private String defaultContentType;

    private Map<Class<? extends Exception>, ?> exceptionHandlers = emptyMap();

    private List<HttpMessageConverter<?>> httpMessageConverters;

    private MessageInterpolator messageInterpolator;

    private MessageSource messageSource;

    private boolean withDefaultHandlers = true;

    private boolean withDefaultMessageSource = true;


    @Override
    @SuppressWarnings("unchecked")
    public RestHandlerExceptionResolver getObject() {

        RestHandlerExceptionResolverBuilder builder = createBuilder()
                .messageSource(messageSource)
                .messageInterpolator(messageInterpolator)
                .httpMessageConverters(httpMessageConverters)
                .contentNegotiationManager(contentNegotiationManager)
                .defaultContentType(defaultContentType)
                .withDefaultHandlers(withDefaultHandlers)
                .withDefaultMessageSource(withDefaultMessageSource);

        for (Map.Entry<Class<? extends Exception>, ?> entry : exceptionHandlers.entrySet()) {
            Class<? extends Exception> exceptionClass = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof RestExceptionHandler) {
                builder.addHandler(exceptionClass, (RestExceptionHandler) value);

            } else {
                builder.addErrorMessageHandler(exceptionClass, parseHttpStatus(value));
            }
        }

        return builder.build();
    }

    @Override
    public Class<?> getObjectType() {
        return RestHandlerExceptionResolver.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    RestHandlerExceptionResolverBuilder createBuilder() {
        return RestHandlerExceptionResolver.builder();
    }

    HttpStatus parseHttpStatus(Object value) {
        Assert.notNull(value, "Values of the exceptionHandlers map must not be null");

        if (value instanceof HttpStatus) {
            return (HttpStatus) value;

        } else if (value instanceof Integer) {
            return HttpStatus.valueOf((int) value);

        } else if (value instanceof String) {
            return HttpStatus.valueOf(Integer.valueOf((String) value));

        } else {
            throw new IllegalArgumentException(String.format(
                    "Values of the exceptionHandlers maps must be instance of ErrorResponseFactory, HttpStatus, " +
                    "String, or int, but %s given", value.getClass()));
        }
    }
}
