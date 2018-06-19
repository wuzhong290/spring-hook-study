package com.demo.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/**
JDK动态代理代理类
 */
public class CarHandler implements InvocationHandler{
    /** 真实类的对象 */
    private Object car;
    /**
     * 构造方法赋值给真实的类
     */
    public CarHandler(Object obj){
        this.car = obj;
    }
    /**
     * 代理类执行方法时，调用的是这个方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object res = method.invoke(car, args);
        System.out.println("after");
        return res;
    }
}