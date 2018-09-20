package com.demo.leader.controller;

import com.demo.leader.ExampleClient;
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

    private static final String PATH = "/qg/leader";

    @RequestMapping("/leadertest")
    @ResponseBody
    public String leadertest(@RequestParam String c) {
        ExampleClient example = new ExampleClient(curatorFramework, PATH, "Client #"+c);
        try {
            example.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "leadertest";
    }
}
