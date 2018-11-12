package com.demo.javabase.innerclass;

public class Child extends Parent {
    public static String staticVar;
    public int i;
    public String unStaticVar;
    public final String finalVar;
    static {
        System.out.println("Child 静态代码块");
        staticVar = "staticVar Child";
    }

    {
        finalVar = "finalVar";
        staticVar = "staticVar Child";
        unStaticVar = "unStaticVar Child";
        System.out.println("Child 非静态代码块");
    }

    public Child() {
        System.out.println("Child 构成方法执行");
    }

    public Child(int i) {
        this.i = i;
        System.out.println("Child 构成方法(unStaticVar)执行" +this.unStaticVar);
    }

    public Child(String unStaticVar) {
        this.unStaticVar = unStaticVar;
        System.out.println("Child 构成方法(unStaticVar)执行" +this.unStaticVar);
    }

    public static void  staticMethod(){
        System.out.println("Child 静态方法staticMethod执行");
    }

    @Override
    public void  unStaticMethod(){
        System.out.println("Child 非静态方法unStaticMethod执行");
    }

    public void  unStaticMethod(String a){
        System.out.println("Child 非静态方法unStaticMethod(a)执行");
    }

    public static void main(String[] args) {
        String unStaticVar = new String("1111111111111");
        System.out.println(unStaticVar.hashCode());
        System.out.println("================");
        Child.staticMethod();
        System.out.println("================");
        System.out.println(Child.staticVar);
        System.out.println("================");
        Child child = new Child(unStaticVar);
        System.out.println(child.unStaticVar);
        System.out.println(child.finalVar);
        System.out.println(Child.staticVar);
        child.unStaticMethod();
        int int1 = 12;
        int int2 = 12;
        Integer Integer1 = new Integer(12);
        Integer Integer2 = new Integer(12);
        Integer Integer3 = new Integer(127);

        Integer a1 = 127;
        Integer b1 = 127;

        Integer a = 128;
        Integer b = 128;

        String s1 = "str";
        String s2 = "str";
        String str1 = new String("str");
        String str2 = new String("str");

        System.out.println("int1==int2:" + (int1 == int2));
        System.out.println("int1==Integer1:" + (int1 == Integer1));
        System.out.println("Integer1==Integer2:" + (Integer1 == Integer2));
        System.out.println("Integer3==b1:" + (Integer3 == b1));
        System.out.println("a1==b1:" + (a1 == b1));
        System.out.println("a==b:" + (a == b));


        System.out.println("s1==s2:" + (s1 == s2));
        System.out.println("s1==str1:" + (s1 == str1));
        System.out.println("str1==str2:" + (str1 == str2));
    }
}
