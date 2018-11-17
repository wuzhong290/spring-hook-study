package com.demo.javabase.bitset;

import java.util.BitSet;

public class Main {

    public static void main(String[] args) {
        BitSet bits = new BitSet();
        System.out.println(bits.size());
        System.out.println(bits.length());
        System.out.println(bits.cardinality());
        System.out.println("===============");
        bits.set(8);
        bits.set(100000000);
        System.out.println(bits.size());
        System.out.println(bits.length());
        System.out.println(bits.cardinality());
        System.out.println("===============");
        bits.clear(bits.length()-1);
        System.out.println(bits.size());
        System.out.println(bits.length());
        System.out.println(bits.cardinality());
    }
}
