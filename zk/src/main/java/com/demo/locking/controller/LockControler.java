package com.demo.locking.controller;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.demo.locking.lock.*;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/lock")
public class LockControler {

    @Autowired
    private CuratorFramework curatorFramework;

    private static final String     PATH = "/qg/locks";

    @RequestMapping("/locktest")
    @ResponseBody
    public String locktest() {
        final FakeLimitedResource resource = new FakeLimitedResource();
        ZkClientThatLocks example = new ZkClientThatLocks(curatorFramework, PATH, resource, "Client");
        for ( int j = 0; j < 3; ++j )
        {
            try {
                example.doWork(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "locktest";
    }
}
