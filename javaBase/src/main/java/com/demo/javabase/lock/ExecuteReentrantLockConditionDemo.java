package com.demo.javabase.lock;

public class ExecuteReentrantLockConditionDemo {

    public static void main(String[] args) {
        final ReentrantLockConditionDemo rd = new ReentrantLockConditionDemo();
        // 创建1个消费者线程
        for (int i = 0; i < 1; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        rd.get();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }).start();
        }

        // 创建10个生产者线程
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        rd.put("bread");
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}