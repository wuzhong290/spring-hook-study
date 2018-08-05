package com.demo.javabase.innerclass;

public class Out3 {
    private static int a;
    private int b;

    private Object obj = new Object() {
        private String name = "匿名内部类";
        @Override
        public String toString() {
            return name;
        }
    };

    public void test() {
        Object obj = new Object() {
            @Override
            public String toString() {
                System.out.println(b);
                return String.valueOf(a);
            }
        };
        System.out.println(obj.toString());
    }
}