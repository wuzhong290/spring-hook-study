package com.demo.cache;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class LruTest1 {
    public static void main(String[] args) {
        EvictionListener<String, String> listener = new EvictionListener<String, String>() {
            @Override
            public void onEviction(String key, String value) {
                System.out.println("Evicted key=" + key + ", value=" + value);
            }
        };
        ConcurrentMap<String, String> cache = new ConcurrentLinkedHashMap
                .Builder<String, String>()
                .maximumWeightedCapacity(10)
                .listener(listener)
                .build();

        for (int i = 0; i < 150; i++) {
            int j = 1024;
            j = j + i;
            cache.put(String.valueOf(j), "nihao" + i);
        }
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + "====" + value);
        }
        System.out.println(cache.get("1025"));
        cache.remove("1026");
    }
}
