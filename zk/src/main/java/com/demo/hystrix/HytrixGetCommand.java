package com.demo.hystrix;

import com.demo.discovery.client.finder.ServiceFinder;
import com.demo.discovery.util.RestTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 *  restful get
 * @param <T>
 */
public class HytrixGetCommand<T> extends HytrixBaseCommand<T> {

    public HytrixGetCommand(RestTemplate restTemplate, ServiceFinder serviceFinder, String serviceName, Object request, Class<T> responseType) {
        super(restTemplate, serviceFinder, serviceName, request, responseType);
    }


    /**
     * 直接传入URL
     * @param restTemplate
     * @param url
     * @param serviceName
     * @param request
     * @param responseType
     */
    public HytrixGetCommand(RestTemplate restTemplate, String url, String serviceName,  Object request, Class<T> responseType) {
        super(restTemplate, url, serviceName, request, responseType);
    }

    @Override
    T getOrPost(String path, Object request, Class<T> responseType, Map<String,String> extraHeaders) throws Exception {
        return RestTemplateUtils.get(super.restTemplate, path, request, responseType, extraHeaders);
    }


}