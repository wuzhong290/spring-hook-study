package com.demo.javabase.innerclass;

public class Out2 {
    private static int a;
    private int b;

    public void test(final int c) {
        final int d = 1;
        class Inner {
            public void print() {
                System.out.println(a);
                System.out.println(b);
                System.out.println(c);
                System.out.println(d);
            }
        }
    }

    public static void testStatic(final int c) {
        final int d = 1;
        class Inner {
            public void print() {
                System.out.println(a);
                //定义在静态方法中的局部类不可以访问外部类的实例变量
                //System.out.println(b);
                System.out.println(c);
                System.out.println(d);
            }
        }
    }
}