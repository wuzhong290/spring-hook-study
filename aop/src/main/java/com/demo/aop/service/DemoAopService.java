package com.demo.aop.service;

import com.demo.aop.aop.TestAop;
import org.springframework.stereotype.Service;

/**
 * Created by wuzhong on 2017/11/3.
 */
@Service
public class DemoAopService {

    @TestAop
    public void testAop(){
        System.out.println("test aop service.................");
    }
}
