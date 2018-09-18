package com.demo.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * 服务调用统计
 */
@Service
public class ServiceCallAudit implements ApplicationEventPublisherAware{
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private ApplicationEventPublisher publisher;

    /**
     * 成功调用统计
     *  @param srcServiceName 来源的服务名称
     *  @param  serviceName 调用的服务名称
     *  @param cost 服务调用时间
     *  @param path 服务路径
     */
    public void audit(String serviceName, String srcServiceName, long cost,  String path) {
        log.info(serviceName, path, srcServiceName,  null, cost);
    }


    /**
     *  失败调用统计
     *  @param  srcServiceName 来源的服务名称
     *  @param  serviceName 调用的服务名称
     *  @param  path 服务路径
     *  @param  e 异常信息
     */
    public void auditFailed(String serviceName, String srcServiceName, String path,Throwable e) {
        log.info(serviceName, path, srcServiceName, e);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }
}
