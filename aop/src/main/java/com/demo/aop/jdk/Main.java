package com.demo.aop.jdk;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        CarImpl carImpl = new CarImpl();
        CarHandler carHandler = new CarHandler(carImpl);
        Car proxy = (Car) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                carImpl.getClass().getInterfaces(),
                carHandler);
        proxy.run();
    }
}
