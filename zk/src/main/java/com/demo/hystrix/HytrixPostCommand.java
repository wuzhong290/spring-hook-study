package com.demo.hystrix;

import com.demo.discovery.client.finder.ServiceFinder;
import com.demo.discovery.util.RestTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 *  post
 * @param <T>
 */
public class HytrixPostCommand<T> extends HytrixBaseCommand<T> {

    public HytrixPostCommand(RestTemplate restTemplate, ServiceFinder serviceFinder, String serviceName, Object request, Class<T> responseType) {
        super(restTemplate, serviceFinder, serviceName, request, responseType);
    }

    public HytrixPostCommand(RestTemplate restTemplate, String url,  String serviceName, Object request, Class<T> responseType) {
        super(restTemplate, url, serviceName, request, responseType);
    }


    @Override
    T getOrPost(String path, Object request, Class<T> responseType,Map<String,String> extraHeaders) throws Exception {
        return RestTemplateUtils.post(super.restTemplate, path, request, responseType, extraHeaders);
    }


}