package com.demo.aop.controller;

import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactory;

public class Test {


    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(null);
        proxyFactory.addAdvice(new Advice(){

        });
        proxyFactory.getProxy();
    }
}
