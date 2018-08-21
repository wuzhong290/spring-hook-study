package com.demo.javabase.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ListIteratorExample {
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
        ListIterator<Integer> it = list.listIterator();
        while (it.hasNext())
        {
            Integer integer = (Integer) it.next();
            if(integer == 6542){
                it.remove();
                it.add(6543);
            }
        }
        Iterator<Integer> it1 = list.iterator();
        while (it1.hasNext())
        {
            Integer integer = (Integer) it1.next();
            System.out.println(integer);
        }
    }
}
