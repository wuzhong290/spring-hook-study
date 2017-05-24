package com.demo.converters.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.util.IOUtils;
import com.demo.converters.annotation.JsonDemo;
import com.demo.converters.model.Person;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by thinkpad on 2017/5/24.
 */
public class JsonDemoConverter extends AbstractHttpMessageConverter<Object> {
    public JsonDemoConverter() {
        super(MediaType.ALL);
    }

    @Override

    protected boolean supports(Class<?> aClass) {
        boolean s = Person.class.isAnnotationPresent(JsonDemo.class);
        System.out.println("supports:"+s);
        return s;
    }

    @Override
    protected Object readInternal(Class<? extends Object> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream in = httpInputMessage.getBody();
        Object str = JSON.parseObject(in, IOUtils.UTF8, aClass, Feature.AllowSingleQuotes);
        System.out.println("readInternal:"+str);
        return str;
    }

    @Override
    protected void writeInternal(Object s, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        System.out.println("writeInternal:"+s);
        HttpHeaders headers = httpOutputMessage.getHeaders();
        ByteArrayOutputStream outnew = new ByteArrayOutputStream();
        int len = JSON.writeJSONString(outnew, IOUtils.UTF8, s);
        headers.setContentLength((long)len);
        OutputStream out = httpOutputMessage.getBody();
        outnew.writeTo(out);
        outnew.close();
    }
}
