package com.demo.discovery.client.finder;

import com.demo.discovery.server.model.InstanceDetails;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.curator.x.discovery.ServiceCache;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.util.*;
import java.util.concurrent.*;

/**
 * 服务发现客户端
 */
public class CuratorXDiscoveryClientWrapper {

    private final Logger logger = LoggerFactory.getLogger(CuratorXDiscoveryClientWrapper.class);
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("curator-serviceCache-thread-%d").build();
    ExecutorService serviceCacheExecutors;
    private ServiceDiscovery<InstanceDetails> serviceDiscovery;
    /**
     * servicename --> servicecache map
     */
    private Map<String, ServiceCache<InstanceDetails>> cacheMap = Maps.newHashMap();
    private List<Closeable> closeableList = Lists.newArrayList();
    private Object lock = new Object();
    private String basePath;
    private String zkAddress;
    private CuratorFramework client;

    /**
     * 构造函数
     *
     * @param client   zookeeper客户端
     * @param basePath zk上服务注册的基本路径
     * @throws Exception 异常
     */
    public CuratorXDiscoveryClientWrapper(CuratorFramework client, String basePath) throws Exception {
        this.serviceCacheExecutors = new ThreadPoolExecutor(30, 30,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        namedThreadFactory);
        this.basePath = basePath;
        this.client = client;
        this.zkAddress = client.getZookeeperClient().getCurrentConnectionString();
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<InstanceDetails>(InstanceDetails.class);
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(client)
                .basePath(basePath)
                .serializer(serializer)
                .build();

        serviceDiscovery.start();
    }


    /**
     * 获取一个服务的所有地址
     *
     * @param serviceName 服务名称
     * @return 所有地址
     * @throws Exception 异常
     */
    public List<String> getAllRequestUrl(String serviceName) throws Exception {
        List<String> path = new LinkedList<>();
        List<ServiceInstance<InstanceDetails>> all = getInstancesByName(serviceName);
        for (ServiceInstance<InstanceDetails> one : all) {
            path.add(one.getPayload().getRequestUrl());
        }
        return path;
    }


    /**
     * 注销这个IP下的这个context下的所有服务实例 用于内部管理
     *
     * @param ip      IP
     * @param context context
     */
    public void unRegister(String ip, String context) throws Exception {

        if (StringUtils.isEmpty(ip) || StringUtils.isEmpty(context)) {
            return;
        }

        Collection<String> serviceNames = serviceDiscovery.queryForNames();
        if (CollectionUtils.isEmpty(serviceNames)) {
            logger.error("can not find any services from zookeeper:{}", zkAddress);
            return;
        }

        int count = 0;
        for (String serviceName : serviceNames) {

            Collection<ServiceInstance<InstanceDetails>> instances = serviceDiscovery.queryForInstances(serviceName);
            if (CollectionUtils.isEmpty(instances)) {
                continue;
            }

            for (ServiceInstance<InstanceDetails> serviceInstance : instances) {
                if (serviceInstance.getAddress().equals(ip) &&
                        serviceInstance.getPayload().getContext().equals(context)) {
                    //注销
                    this.deleteZkPath(serviceInstance.getName(), serviceInstance.getId());
                    count++;
                    logger.debug("unregister: {} success", serviceInstance);
                }
            }
        }

        logger.info("unregister total:{} service instances for ip:{} context:{} success.", count, ip, context);
    }


    private void deleteZkPath(String serviceName, String id) {
        String path = ZKPaths.makePath(this.basePath, serviceName);
        String full_path = ZKPaths.makePath(path, id);
        try {
            this.client.delete().guaranteed().forPath(full_path);
        } catch (Exception ex) {
            logger.error("can not delete path.", ex);
        }
    }


    /**
     * 从zookeeper中获取所有的服务配置
     * promotion:
     * servers:
     * - http://192.168.102.205:8085/promotion
     * services:
     * - queryParmListByIds:/promotion/queryParmListByIds
     * - cancelOrderCouponUse:/coupon/cancelOrderCouponUse
     *
     * @return
     * @throws Exception
     */
    public Map<String, Object> getAllService() throws Exception {

        Map<String, Object> yaml = new HashMap<>();
        //get all service names
        Collection<String> serviceNames = serviceDiscovery.queryForNames();

        if (CollectionUtils.isEmpty(serviceNames)) {
            logger.error("can not find any services from zookeeper:{}", zkAddress);
            return yaml;
        }

        for (String serviceName : serviceNames) {

            Collection<ServiceInstance<InstanceDetails>> instances;
            try {
                instances = serviceDiscovery.queryForInstances(serviceName);
                if (CollectionUtils.isEmpty(instances)) {
                    logger.debug("can not find any service instance by name:{}", serviceName);
                    continue;
                }
            } catch (Exception e) {
                logger.warn("exception happened when query instances for: {}", serviceName, e);
                continue;
            }

            String type = serviceName.substring(0, serviceName.indexOf(".")); //promotion: type
            String name = serviceName.substring(serviceName.indexOf(".") + 1); //method
            Map<String, List<String>> serviceInfoMap;
            if (yaml.containsKey(type)) {
                serviceInfoMap = (Map<String, List<String>>) yaml.get(type);
            } else {
                serviceInfoMap = new HashMap<>();
                yaml.put(type, serviceInfoMap);
            }

            //init servers
            List<String> servers;
            if (serviceInfoMap.containsKey("servers")) {
                servers = serviceInfoMap.get("servers");
            } else {
                servers = new LinkedList<>();
                serviceInfoMap.put("servers", servers);
            }

            //init servers
            List<String> services;
            if (serviceInfoMap.containsKey("services")) {
                services = serviceInfoMap.get("services");
            } else {
                services = new LinkedList<>();
                serviceInfoMap.put("services", services);
            }

            for (ServiceInstance<InstanceDetails> serviceInstance : instances) {
                InstanceDetails instanceDetail = serviceInstance.getPayload();
                /**
                 *   添加server   http://ip:port/context/xxxx
                 */
                String serverUrl = instanceDetail.fetchContextUrl();
                if (!servers.contains(serverUrl)) {
                    servers.add(serverUrl);
                }
                /**
                 *   添加service：  cancelOrderCouponUse:/coupon/cancelOrderCouponUse
                 */
                String serviceUrl = instanceDetail.fetchSubUrl();
                String service = name + ":" + serviceUrl;
                if (!services.contains(service)) {
                    services.add(service);
                }
            }

        }
        return yaml;
    }


    /**
     * 根据服务名称，获取服务
     *
     * @param serviceName 服务的名称
     * @return 服务实例
     * @throws Exception 异常
     */
    public List<ServiceInstance<InstanceDetails>> getInstancesByName(String serviceName) throws Exception {
        ServiceCache<InstanceDetails> cache = cacheMap.get(serviceName);
        if (cache == null) {
            synchronized (lock) {
                cache = cacheMap.get(serviceName);
                if (cache == null) {
                    cache = serviceDiscovery.serviceCacheBuilder().name(serviceName).executorService(this.serviceCacheExecutors).build();
                    cache.start();
                    closeableList.add(cache);
                    cacheMap.put(serviceName, cache);
                }
            }
        }


        return cache.getInstances();
    }


    public synchronized void close() {
        for (Closeable closeable : closeableList) {
            CloseableUtils.closeQuietly(closeable);
        }
    }


}