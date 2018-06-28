package com.demo.rest.service;

public interface RestTrackService {

    /**
     * @param url 执行的请求url
     * @param args 参数信息
     * @param result 结果信息
     * @param resultCode 结果码
     */
    void restTrack(String url, Object[] args, Object result, int resultCode);
}
