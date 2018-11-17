package com.demo.leader.controller;

import com.demo.leader.ExampleClient;
import com.demo.leader.LeaderLatchDemo;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/leader")
public class LeaderControler {

    @Autowired
    private CuratorFramework curatorFramework;

    private static final String PATH = "/qg/leader_selector";

    @RequestMapping("/leaderselector")
    @ResponseBody
    public String leaderSelector(@RequestParam String c) {
        ExampleClient example = new ExampleClient(curatorFramework, PATH, "Client #"+c);
        try {
            example.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "leaderSelector";
    }
    @RequestMapping("/leaderlatch")
    @ResponseBody
    public String leaderlatch() {
        LeaderLatchDemo example = new LeaderLatchDemo(curatorFramework);
        try {
            example.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "leaderlatch";
    }
}
