package com.demo.javabase.sort;

import java.util.Arrays;

public class InsertSort {
    public static void insertSortMinToMax(int[] a) {
        int i, j, insertNote;// 要插入的数据
        //从数组的第二个元素开始循环将数组中的元素插入
        for (i = 1; i < a.length; i++) {
            // 设置数组中的第2个元素为第一次循环要插入的数据
            insertNote = a[i];
            j = i - 1;
            while (j >= 0 && insertNote < a[j]) {
                // 如果要插入的元素小于第j个元素,就将第j个元素向后移动
                a[j + 1] = a[j];
                j--;
            }
            // 直到要插入的元素不小于第j个元素,将insertNote插入到数组中
            a[j + 1] = insertNote;
        }
    }

    public static void insertSortMaxToMin(int[] a) {
        int i, j, insertNote;// 要插入的数据
        //从数组的第二个元素开始循环将数组中的元素插入
        for (i = 1; i < a.length; i++) {
            // 设置数组中的第2个元素为第一次循环要插入的数据
            insertNote = a[i];
            j = i - 1;
            while (j >= 0 && insertNote > a[j]) {
                // 如果要插入的元素大于第j个元素,就将第j个元素向后移动
                a[j + 1] = a[j];
                j--;
            }
            // 直到要插入的元素不小于第j个元素,将insertNote插入到数组中
            a[j + 1] = insertNote;
        }
    }

    public static void main(String[] args) {
        int a[] = { 38,65,97,76,13,27,49 };
        //insertSortMinToMax(a);
        insertSortMaxToMin(a);
        System.out.println(Arrays.toString(a));
    }
}
