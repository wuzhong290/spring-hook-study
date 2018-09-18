package com.demo.discovery.server.controler;

import com.demo.discovery.client.finder.ServiceFinder;
import com.demo.discovery.server.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/zk")
public class ZkControler {

    @Autowired
    private ServiceFinder serviceFinder;

    @RequestMapping("/zktest")
    public String zktest() {
        InstanceDetails instanceDetails = serviceFinder.getService("test.zktest");
        System.out.println("");
        return "zktest";
    }
}
