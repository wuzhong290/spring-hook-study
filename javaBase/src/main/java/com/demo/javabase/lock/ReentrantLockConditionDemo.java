package com.demo.javabase.lock;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockConditionDemo {

    private Lock theLock = new ReentrantLock();
    // 消费者用判断条件
    private Condition full = theLock.newCondition();
    // 生产者用判断条件
    private Condition empty = theLock.newCondition();

    private static List<String> cache = new LinkedList<String>();

    // 生产者线程任务
    public void put(String str) throws InterruptedException {
        Thread.sleep(200);
        // 获取线程锁
        theLock.lock();
        try {
            while (cache.size() != 0) {
                System.out.println("超出缓存容量.暂停写入.");
                // 生产者线程阻塞
                full.await();
                System.out.println("生产者线程被唤醒");
            }
            System.out.println("写入数据");
            cache.add(str);
            // 唤醒消费者
            empty.signal();
        } finally {
            // 锁使用完毕后不要忘记释放
            theLock.unlock();
        }
    }

    // 消费者线程任务
    public void get() throws InterruptedException {
        try {
            while (!Thread.interrupted()) {
                // 获取锁
                theLock.lock();
                while (cache.size() == 0) {
                    System.out.println("缓存数据读取完毕.暂停读取");
                    // 消费者线程阻塞
                    empty.await();
                }
                System.out.println("读取数据");
                cache.remove(0);
                // 唤醒生产者线程
                full.signal();
            }

        } finally {
            // 锁使用完毕后不要忘记释放
            theLock.unlock();
        }
    }

}