package com.demo.javabase.sort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] a = {1, 2, 4, 5, 7, 4, 5 ,3 ,9 ,0};
        System.out.println(Arrays.toString(a));
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] a) {
        if(a.length>0) {
            quickSortMinToMax(a, 0 , a.length-1);
            quickSortMaxToMin(a, 0 , a.length-1);
        }
    }

    private static void quickSortMinToMax(int[] a, int low, int high) {
        //2, 存
        int i = low;
        int j = high;
        //3,key
        int key = a[ low ];
        //4，完成一趟排序
        while( i< j) {
            //4.1 ，从右往左找到第一个小于key的数
            while(i<j && a[j] > key){
                j--;
            }
            // 4.2 从左往右找到第一个大于key的数
            while( i<j && a[i] <= key) {
                i++;
            }
            //4.3 交换
            if(i<j) {
                a[i] = a[i]^a[j];
                a[j] = a[i]^a[j];
                a[i] = a[i]^a[j];
            }
        }
        if(low < i){
            // 4.4，调整key的位置
            a[low] = a[low]^a[i];
            a[i] = a[low]^a[i];
            a[low] = a[low]^a[i];
            //5, 对key左边的数快排
            quickSortMinToMax(a, low, i-1);
        }
        if(i < high){
            //6, 对key右边的数快排
            quickSortMinToMax(a, i+1, high);
        }
    }


    private static void quickSortMaxToMin(int[] a, int low, int high) {
        //2, 存
        int i = low;
        int j = high;
        //3,key
        int key = a[ low ];
        //4，完成一趟排序
        while( i< j) {
            //4.1 ，从右往左找到第一个大于key的数
            while(i<j && a[j] < key){
                j--;
            }
            // 4.2 从左往右找到第一个小于key的数
            while( i<j && a[i] >= key) {
                i++;
            }
            //4.3 交换
            if(i<j) {
                a[i] = a[i]^a[j];
                a[j] = a[i]^a[j];
                a[i] = a[i]^a[j];
            }
        }
        if(low < i){
            // 4.4，调整key的位置
            a[low] = a[low]^a[i];
            a[i] = a[low]^a[i];
            a[low] = a[low]^a[i];
            //5, 对key左边的数快排
            quickSortMaxToMin(a, low, i-1);
        }
        if(i < high){
            //6, 对key右边的数快排
            quickSortMaxToMin(a, i+1, high);
        }
    }
}
