package com.demo.aop.cglib;

public class Main {
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        CarNoInterface carNoInterface = (CarNoInterface)cglibProxy.getInstance(new CarNoInterface());
        carNoInterface.run();
    }

}
