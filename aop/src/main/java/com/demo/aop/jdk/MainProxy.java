package com.demo.aop.jdk;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;

public class MainProxy {

    public static void main(String[] args) {
        //代理对象需要的实现的接口
        Class[] interfaces = CarImpl.class.getInterfaces();
        ProxyFactory proxyFactory = new ProxyFactory(interfaces);
        //决定采取哪种代理方式，如果设为true,代理方式就是CGlib
        //proxyFactory.setProxyTargetClass(true);
        proxyFactory.setTarget(new CarImpl());
        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
            System.out.println("事物开始");
            Object proceed = invocation.proceed();
            System.out.println("事物结束");
            return  proceed;
        });
        Car car = (Car)proxyFactory.getProxy();
        car.run();
    }

}
