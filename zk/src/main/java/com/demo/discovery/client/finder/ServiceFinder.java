package com.demo.discovery.client.finder;

import com.demo.discovery.server.model.InstanceDetails;

/**
 * 服务发现者
 */
public interface ServiceFinder {

    /**
     *
     * @param serviceName  服务名称
     * @return  服务
     */
     InstanceDetails getService(String serviceName);
}
