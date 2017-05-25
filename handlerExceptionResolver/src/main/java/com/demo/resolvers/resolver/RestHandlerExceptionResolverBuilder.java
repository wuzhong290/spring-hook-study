/*
 * Copyright 2014 Jakub Jirutka <jakub@jirutka.cz>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.demo.resolvers.resolver;

import com.demo.resolvers.handlers.AbstractRestExceptionHandler;
import com.demo.resolvers.handlers.ErrorMessageRestExceptionHandler;
import com.demo.resolvers.handlers.RestExceptionHandler;
import com.demo.resolvers.interpolators.MessageInterpolator;
import com.demo.resolvers.interpolators.MessageInterpolatorAware;
import com.demo.resolvers.support.MapUtils;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.HierarchicalMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.NONE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.StringUtils.hasText;

@Setter
@Accessors(fluent=true)
@SuppressWarnings("unchecked")
public class RestHandlerExceptionResolverBuilder {

    public static final String DEFAULT_MESSAGES_BASENAME = "classpath:/com/demo/resolvers/messages";

    private final Map<Class, RestExceptionHandler> exceptionHandlers = new HashMap<>();

    @Setter(NONE) // to not conflict with overloaded setter
    private MediaType defaultContentType;

    private ContentNegotiationManager contentNegotiationManager;

    private List<HttpMessageConverter<?>> httpMessageConverters;


    private MessageInterpolator messageInterpolator;

    private MessageSource messageSource;

    private boolean withDefaultHandlers = true;

    private boolean withDefaultMessageSource = true;


    public RestHandlerExceptionResolver build() {

        if (withDefaultMessageSource) {
            if (messageSource != null) {
                // set default message source as top parent
                HierarchicalMessageSource messages = resolveRootMessageSource(messageSource);
                if (messages != null) {
                    messages.setParentMessageSource(createDefaultMessageSource());
                }
            } else {
                messageSource = createDefaultMessageSource();
            }
        }

        if (withDefaultHandlers) {
            // add default handlers
            MapUtils.putAllIfAbsent(exceptionHandlers, getDefaultHandlers());
        }

        // initialize handlers
        for (RestExceptionHandler handler : exceptionHandlers.values()) {
            if (messageSource != null && handler instanceof MessageSourceAware) {
                ((MessageSourceAware) handler).setMessageSource(messageSource);
            }
            if (messageInterpolator != null && handler instanceof MessageInterpolatorAware) {
                ((MessageInterpolatorAware) handler).setMessageInterpolator(messageInterpolator);
            }
        }

        RestHandlerExceptionResolver resolver = new RestHandlerExceptionResolver();
        resolver.setExceptionHandlers((Map) exceptionHandlers);

        if (httpMessageConverters != null) {
            resolver.setMessageConverters(httpMessageConverters);
        }
        if (contentNegotiationManager != null) {
            resolver.setContentNegotiationManager(contentNegotiationManager);
        }
        if (defaultContentType != null) {
            resolver.setDefaultContentType(defaultContentType);
        }
        resolver.afterPropertiesSet();

        return resolver;
    }

    /**
     * The default content type that will be used as a fallback when the requested content type is
     * not supported.
     */
    public RestHandlerExceptionResolverBuilder defaultContentType(MediaType mediaType) {
        this.defaultContentType = mediaType;
        return this;
    }

    /**
     * The default content type that will be used as a fallback when the requested content type is
     * not supported.
     */
    public RestHandlerExceptionResolverBuilder defaultContentType(String mediaType) {
        defaultContentType( hasText(mediaType) ? MediaType.parseMediaType(mediaType) : null );
        return this;
    }

    /**
     * Registers the given exception handler for the specified exception type. This handler will be
     * also used for all the exception subtypes, when no more specific mapping is found.
     *
     * @param exceptionClass The exception type handled by the given handler.
     * @param exceptionHandler An instance of the exception handler for the specified exception
     *                         type or its subtypes.
     */
    public <E extends Exception> RestHandlerExceptionResolverBuilder addHandler(
            Class<? extends E> exceptionClass, RestExceptionHandler<E, ?> exceptionHandler) {

        exceptionHandlers.put(exceptionClass, exceptionHandler);
        return this;
    }

    /**
     * Same as {@link #addHandler(Class, RestExceptionHandler)}, but the exception type is
     * determined from the handler.
     */
    public <E extends Exception>
            RestHandlerExceptionResolverBuilder addHandler(AbstractRestExceptionHandler<E, ?> exceptionHandler) {

        return addHandler(exceptionHandler.getExceptionClass(), exceptionHandler);
    }

    public RestHandlerExceptionResolverBuilder addErrorMessageHandler(
            Class<? extends Exception> exceptionClass, HttpStatus status) {

        return addHandler(new ErrorMessageRestExceptionHandler<>(exceptionClass, status));
    }


    HierarchicalMessageSource resolveRootMessageSource(MessageSource messageSource) {

        if (messageSource instanceof HierarchicalMessageSource) {
            MessageSource parent = ((HierarchicalMessageSource) messageSource).getParentMessageSource();

            return parent != null ? resolveRootMessageSource(parent) : (HierarchicalMessageSource) messageSource;

        } else {
            return null;
        }
    }

    private Map<Class, RestExceptionHandler> getDefaultHandlers() {

        Map<Class, RestExceptionHandler> map = new HashMap<>();

        addHandlerTo( map, HttpMediaTypeNotAcceptableException.class, NOT_ACCEPTABLE );
        addHandlerTo( map, MissingServletRequestParameterException.class, BAD_REQUEST );
        addHandlerTo( map, ServletRequestBindingException.class, BAD_REQUEST );
        addHandlerTo( map, ConversionNotSupportedException.class, INTERNAL_SERVER_ERROR );
        addHandlerTo( map, TypeMismatchException.class, BAD_REQUEST );
        addHandlerTo( map, HttpMessageNotReadableException.class, UNPROCESSABLE_ENTITY );
        addHandlerTo( map, HttpMessageNotWritableException.class, INTERNAL_SERVER_ERROR );
        addHandlerTo( map, MissingServletRequestPartException.class, BAD_REQUEST );
        addHandlerTo(map, Exception.class, INTERNAL_SERVER_ERROR);

        // this class didn't exist before Spring 4.0
        try {
            Class clazz = Class.forName("org.springframework.web.servlet.NoHandlerFoundException");
            addHandlerTo(map, clazz, NOT_FOUND);
        } catch (ClassNotFoundException ex) {
            // ignore
        }
        return map;
    }

    private void addHandlerTo(Map<Class, RestExceptionHandler> map, Class exceptionClass, HttpStatus status) {
        map.put(exceptionClass, new ErrorMessageRestExceptionHandler(exceptionClass, status));
    }

    private MessageSource createDefaultMessageSource() {

        ReloadableResourceBundleMessageSource messages = new ReloadableResourceBundleMessageSource();
        messages.setBasename(DEFAULT_MESSAGES_BASENAME);
        messages.setDefaultEncoding("UTF-8");
        messages.setFallbackToSystemLocale(false);

        return messages;
    }
}
