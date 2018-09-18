package com.demo.discovery.client.strategy;


import com.demo.discovery.util.LocalIp;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Random robin 选择
 *  本地优先
 */
public class RoundRobinLocalFirstStrategy implements Strategy {
    private final AtomicInteger index = new AtomicInteger(0);
    private final static  String localIP = LocalIp.getLocalIp();

    @Override
    public <T> T getInstance(List<T> all) {
        if (all == null || all.size() == 0) {
            return null;
        }


        /** 如果本地IP匹配成功，则优先返回本地*/
        if( all.get(0) instanceof ServiceInstance<?>  ){
            for(T ins : all){
                ServiceInstance<?> serviceInstance = (ServiceInstance<?>)ins;
                String remoteIp = serviceInstance.getAddress();
                if(StringUtils.equals(localIP,remoteIp)){
                    return ins;
                }
            }
        }


        int thisIndex = Math.abs(index.getAndIncrement());
        return all.get(thisIndex % all.size());
    }


}