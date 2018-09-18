package com.demo.discovery.client.finder;

import com.demo.discovery.client.strategy.Strategy;
import com.demo.discovery.server.model.InstanceDetails;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * zookeeper发现
 */
public class ZookeeperFinder implements ServiceFinder {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperFinder.class);

    private Strategy strategry;
    private CuratorXDiscoveryClientWrapper curatorXDiscoveryClientWrapper;


    @Override
    public InstanceDetails getService(String serviceName)  {
        try {
            List<ServiceInstance<InstanceDetails>> all = this.curatorXDiscoveryClientWrapper.getInstancesByName(serviceName);
            ServiceInstance<InstanceDetails> serviceInstance = strategry.getInstance(all);
            //make sure instance is ok
            if (serviceInstance != null  && serviceInstance.getPayload() != null && !StringUtils.isEmpty(serviceInstance.getPayload().getRequestUrl())) {
                logger.debug("find instance: {} by service name: {}", serviceInstance.getPayload(), serviceName);
                return serviceInstance.getPayload();
            }
        } catch (Exception e) {
            logger.error("find instance from zookeeper exception happened.", e);
        }
        return null;
    }


    public void setCuratorXDiscoveryClientWrapper(CuratorXDiscoveryClientWrapper curatorXDiscoveryClientWrapper) {
        this.curatorXDiscoveryClientWrapper = curatorXDiscoveryClientWrapper;
    }

    public void setStrategry(Strategy strategry) {
        this.strategry = strategry;
    }
}
