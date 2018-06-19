package com.demo.aop.jdk;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;

public class MainProxy {

    public static void main(String[] args) {
        //代理对象需要的实现的接口
        final Class[] interfaces=new Class[]{Car.class};
        ProxyFactory proxyFactory = new ProxyFactory(interfaces);
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
