package com.demo.javabase.sort;

import java.util.Arrays;
import java.util.BitSet;

public class BitSetSort {

    public static void main(String[] args) {
        int[] a = {1, 2, 4, 5, 7, 4, 5 ,3 ,9 ,0};
        System.out.println(Arrays.toString(a));
        BitSet used = new BitSet();
        for (int i = 0; i < a.length; i++) {
            used.set(a[i]);
        }

        for (int i = used.nextSetBit(0); i >= 0; i = used.nextSetBit(i + 1)) {
            System.out.print(i + ", ");
        }
    }
}
