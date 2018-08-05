package com.demo.javabase.innerclass;

public class Out1 {
    private static int a;
    private int b;

    public class Inner {
        final static  int c=1;
        public void print() {
            System.out.println(a);
            System.out.println(b);
        }
    }

    public static void main(String[] args) {
        System.out.println(Inner.c);
    }
}
