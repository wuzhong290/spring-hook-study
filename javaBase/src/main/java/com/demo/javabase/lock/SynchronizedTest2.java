package com.demo.javabase.lock;

public class SynchronizedTest2 {

    private static Integer i = new Integer(0);

    private final Integer j = 0;

    private  void test(){
        synchronized (i){
            /**
             javac -g -encoding utf-8 SynchronizedTest2.java
             ++i等价于i = Integer.valueOf(i.intValue() +1);
             javap -p -v SynchronizedTest2.class可以说明为什么两个等价
             */
            ++i;
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        new SynchronizedTest2().test();
    }
}
