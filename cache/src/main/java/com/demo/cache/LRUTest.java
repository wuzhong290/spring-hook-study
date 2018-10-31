package com.demo.cache;

import org.apache.commons.collections.OrderedMapIterator;
import org.apache.commons.collections.map.LRUMap;

public class LRUTest {

    public static void main(String[] args) {
        LRUMap lruMap = new LRUMap(2);
        lruMap.put("a1", "1");
        lruMap.put("a2", "2");
        lruMap.get("a1");
        lruMap.put("a3", "3");
        System.out.println(lruMap);
        OrderedMapIterator iterator = lruMap.orderedMapIterator();
        while (iterator.hasNext()){
            Object ob = iterator.next();
            System.out.println(ob);
        }
    }
}
