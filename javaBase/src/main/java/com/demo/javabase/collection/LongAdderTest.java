package com.demo.javabase.collection;


import java.util.concurrent.atomic.LongAdder;

public class LongAdderTest {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.add(1);
        System.out.println(longAdder.sum());
    }
}
