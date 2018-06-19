package com.demo.aop.cglib;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

public class MainProxy {

    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(CarNoInterface.class);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.setTarget(new CarNoInterface());
        proxyFactory.addAdvice(new MethodInterceptor(){

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("事物开始");
                Object proceed = invocation.proceed();
                System.out.println("事物结束");
                return  proceed;
            }
        });
        CarNoInterface car = (CarNoInterface)proxyFactory.getProxy();
        car.run();
    }

}
