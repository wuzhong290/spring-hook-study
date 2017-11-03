package com.demo.aop.controller;

import com.demo.aop.service.DemoAopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wuzhong on 2017/11/3.
 */
@RestController
@RequestMapping("/aop")
public class DemoAopController {

    @Autowired
    private DemoAopService demoAopService;
    @RequestMapping("/testAop")
    public void testAop(){
        demoAopService.testAop();
    }
}
