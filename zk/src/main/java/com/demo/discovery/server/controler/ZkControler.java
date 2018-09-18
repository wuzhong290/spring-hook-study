package com.demo.discovery.server.controler;

import com.demo.aop.monitor.ThreadProfileInterceptor;
import com.demo.discovery.client.finder.ServiceFinder;
import com.demo.discovery.server.model.InstanceDetails;
import com.demo.hystrix.AsyncFuture;
import com.demo.hystrix.HytrixGetCommand;
import com.demo.statistics.ServiceCallAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Controller
@RequestMapping("/zk")
public class ZkControler {

    @Autowired
    private ServiceFinder serviceFinder;
    @Autowired
    private ServiceCallAudit serviceCallAudit;
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/zktest")
    @ResponseBody
    public String zktest() {
        InstanceDetails instanceDetails = serviceFinder.getService("spring-hook-study.zktest");
        System.out.println("");
        return "zktest";
    }
    @RequestMapping("/zkHytrix")
    @ResponseBody
    public String zkHytrix() {
        InstanceDetails instanceDetails = serviceFinder.getService("spring-hook-study.zktest");
        HytrixGetCommand<String> command = new HytrixGetCommand<>(restTemplate, instanceDetails.getRequestUrl(), instanceDetails.getServiceName(), null, String.class);
        command.setFallback(null);
        command.setAudit(serviceCallAudit);
        command.setSrcServiceName(ThreadProfileInterceptor.getServiceName());
        Future<String> future = command.queue();
        try {
            return new AsyncFuture<>(future, instanceDetails.getServiceName(), null).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

}
