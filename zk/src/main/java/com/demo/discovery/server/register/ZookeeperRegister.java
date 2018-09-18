package com.demo.discovery.server.register;

import com.demo.discovery.server.annotation.ServiceDesc;
import com.demo.discovery.server.model.InstanceDetails;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperRegister implements IRegister {
    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperRegister.class);

    private final ServiceDiscovery<InstanceDetails> serviceDiscovery;
    /**
     * 服务注册
     * @param client curator客户端
     * @param basePath zk的path前缀
     * @throws Exception
     */
    public ZookeeperRegister(CuratorFramework client, String basePath) throws Exception {
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<InstanceDetails>(InstanceDetails.class);

        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .basePath(basePath)
                .serializer(serializer)
                .build();

        serviceDiscovery.start();

        LOG.info("zookeeper ServiceRegister start success");
    }
    @Override
    public void register(String context, String listenAddress, String serviceType, String methodName, String method_path, String controller_path, ServiceDesc serviceDesc) {
        boolean degrade = serviceDesc == null ? false : serviceDesc.degrade();
        InstanceDetails.InstanceDetailBuilder  builder = new InstanceDetails.InstanceDetailBuilder();
        builder.linstenAddress(listenAddress)
                .context(context)
                .serviceType(serviceType)
                .methodName(methodName)
                .controllerRequestMapping(controller_path)
                .methodReuqestMapping(method_path)
                .degrade(degrade);

        InstanceDetails instanceDetail = builder.build();
        try {
            ServiceInstance<InstanceDetails> serviceInstance = ServiceInstance.<InstanceDetails>builder()
                    .name(instanceDetail.getServiceName())
                    .address(listenAddress.split(":")[0])
                    .port(Integer.parseInt(listenAddress.split(":")[1]))
                    .payload(instanceDetail)
                    .build();

            this.serviceDiscovery.registerService(serviceInstance);
        } catch (Exception e) {
            LOG.error("register service : {} error.", e.getMessage());
        }
    }
}
