package com.demo.javabase.collection;

import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListDemo {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.add("1111");

        linkedList.get(0);

        linkedList.remove(0);

        ListIterator  listIterator = linkedList.listIterator();
    }
}
