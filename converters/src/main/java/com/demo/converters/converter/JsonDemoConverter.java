package com.demo.converters.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.demo.converters.annotation.Json;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by thinkpad on 2017/5/24.
 */
public class JsonDemoConverter extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object>{
    private static final Logger LOG = LoggerFactory.getLogger(JsonDemoConverter.class);

    private final static Charset UTF8  = Charset.forName("UTF-8");

    public JsonDemoConverter() {
        super(MediaType.ALL);
    }

    @Override

    protected boolean supports(Class<?> aClass) {
        return aClass.isAnnotationPresent(Json.class);
    }

    @Override
    protected Object readInternal(Class<? extends Object> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream in = httpInputMessage.getBody();
        byte[] inByte = IOUtils.toByteArray(in);
        Object str = JSON.parseObject(new String(Base64.decodeBase64(inByte),UTF8),aClass, Feature.AllowSingleQuotes);
        return str;
    }

    @Override
    protected void writeInternal(Object s, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        HttpHeaders headers = httpOutputMessage.getHeaders();
        ByteArrayOutputStream outnew = new ByteArrayOutputStream();
        int len = JSON.writeJSONString(outnew, UTF8, s);
        headers.setContentLength((long)len);
        OutputStream out = httpOutputMessage.getBody();
        outnew.writeTo(out);
        outnew.close();
    }

    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return (type instanceof Class ? canRead((Class<?>) type, mediaType) : canRead(mediaType));
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream in = inputMessage.getBody();
        byte[] inByte = IOUtils.toByteArray(in);
        Object str = JSON.parseObject(new String(Base64.decodeBase64(inByte),UTF8),type, Feature.AllowSingleQuotes);
        return str;
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return super.canWrite(clazz, mediaType);
    }

    public void write(Object t, //
                      Type type, //
                      MediaType contentType, //
                      HttpOutputMessage outputMessage //
    ) throws IOException, HttpMessageNotWritableException {

        HttpHeaders headers = outputMessage.getHeaders();
        if (headers.getContentType() == null) {
            if (contentType == null || contentType.isWildcardType() || contentType.isWildcardSubtype()) {
                contentType = getDefaultContentType(t);
            }
            if (contentType != null) {
                headers.setContentType(contentType);
            }
        }
        if (headers.getContentLength() == -1) {
            Long contentLength = getContentLength(t, headers.getContentType());
            if (contentLength != null) {
                headers.setContentLength(contentLength);
            }
        }
        writeInternal(t, outputMessage);
        outputMessage.getBody().flush();
    }
}
