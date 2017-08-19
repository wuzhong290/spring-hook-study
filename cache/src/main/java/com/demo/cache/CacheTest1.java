package com.demo.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuzhong on 2017/8/19.
 */
public class CacheTest1 {
    public static void main(String[] args) {
        Cache<Integer, Integer> graphs = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .maximumSize(10)
                .recordStats()
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        System.out.println(notification.getKey() + " was removed at "+ System.currentTimeMillis()+", cause is " + notification.getCause());
                    }
                })
                .build();
        Random random = new Random();
        for (int i = 0; i <100; i++){
            graphs.put(i,i);
            System.out.println(i + ":" + System.currentTimeMillis());
            int index = random.nextInt(11);
            graphs.getIfPresent(11);
            try {
                Thread.sleep(index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i <100000; i++){
            System.out.println("end:"+graphs.getIfPresent(95));
            //System.out.println("end:"+graphs.getIfPresent(98));
            //System.out.println("end:"+graphs.getIfPresent(99));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
