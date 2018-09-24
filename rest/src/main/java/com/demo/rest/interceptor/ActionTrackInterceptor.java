package com.demo.rest.interceptor;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class ActionTrackInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        // 加入自定义字段
        headers.add("actionId", String.valueOf(System.currentTimeMillis()));

        // 保证请求继续被执行
        return execution.execute(request, body);
    }
}
