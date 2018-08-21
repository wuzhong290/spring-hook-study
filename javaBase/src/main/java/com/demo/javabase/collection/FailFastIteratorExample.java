package com.demo.javabase.collection;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 1、为了判断这个集合是否被修改，它们使用名为modCount的内部标识，
 * 当集合被修改，该标识也会更新。
 * 2、迭代器每次调用next()方法，都会检查modCount，
 * 如果发现modCount被更新，就会抛出ConcurrentModificationException异常。
 */
public class FailFastIteratorExample {

    public static void main(String[] args) {
        //Creating an ArrayList of integers
        ArrayList<Integer> list = new ArrayList<Integer>();
        //Adding elements to list
        list.add(1452);
        list.add(6854);
        list.add(8741);
        list.add(6542);
        list.add(3845);
        //Getting an Iterator from list
        Iterator<Integer> it = list.iterator();
        while (it.hasNext())
        {
            Integer integer = (Integer) it.next();
            //This will throw ConcurrentModificationException
            list.add(8457);
        }
    }
}
