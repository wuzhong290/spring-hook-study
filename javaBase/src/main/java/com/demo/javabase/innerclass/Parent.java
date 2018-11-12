package com.demo.javabase.innerclass;

public abstract class Parent {
    public static String staticVar;
    public String unStaticVar;

    static {
        System.out.println("Parent 静态代码块");
        staticVar = "staticVar";
    }

    {
        unStaticVar = "unStaticVar";
        System.out.println("Parent 非静态代码块");
    }

    public Parent() {
        System.out.println("Parent 构成方法执行");
    }

    public Parent(String unStaticVar) {
        this.unStaticVar = unStaticVar;
        System.out.println("Parent 构成方法(unStaticVar)执行");
    }

    public static void  staticMethod(){
        System.out.println("Parent 静态方法staticMethod执行");
    }

    public void  unStaticMethod(){
        System.out.println("Parent 非静态方法unStaticMethod执行");
    }
}
