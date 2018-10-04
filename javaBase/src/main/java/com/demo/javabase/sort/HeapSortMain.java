package com.demo.javabase.sort;

import java.util.Arrays;

public class HeapSortMain {

    private static void maxHeapSort(int[] a, int baseIndex, int endIndex){
        int leftIndex = 2 * baseIndex + 1;
        int rightIndex = 2 * baseIndex + 2;
        boolean rightSwitch = false;
        boolean leftSwitch = false;
        if(rightIndex <= endIndex){
            rightSwitch = a[rightIndex] > a[leftIndex] && a[rightIndex] > a[baseIndex];
            leftSwitch = a[leftIndex] > a[rightIndex]  && a[leftIndex] > a[baseIndex];
        }else if(leftIndex <= endIndex){
            leftSwitch = a[leftIndex] > a[baseIndex];
        }
        if(rightSwitch){
            switchMaxItem(a, baseIndex, endIndex, rightSwitch, rightIndex);
        }
        if(leftSwitch){
            switchMaxItem(a, baseIndex, endIndex, leftSwitch, leftIndex);
        }
    }


    private static void switchMaxItem(int[] a, int baseIndex, int endIndex, boolean isSwitch, int rightIndex) {
        if(isSwitch){
            a[baseIndex] = a[baseIndex] ^ a[rightIndex];
            a[rightIndex] = a[baseIndex] ^ a[rightIndex];
            a[baseIndex] = a[baseIndex] ^ a[rightIndex];
            maxHeapSort(a, rightIndex ,endIndex);
        }
    }

    private static void minHeapSort(int[] a, int baseIndex, int endIndex){
        int leftIndex = 2 * baseIndex + 1;
        int rightIndex = 2 * baseIndex + 2;
        boolean rightSwitch = false;
        boolean leftSwitch = false;
        if(rightIndex <= endIndex){
            rightSwitch = a[rightIndex] < a[leftIndex] && a[rightIndex] < a[baseIndex];
            leftSwitch = a[leftIndex] < a[rightIndex]  && a[leftIndex] < a[baseIndex];
        }else if(leftIndex <= endIndex){
            leftSwitch = a[leftIndex] < a[baseIndex];
        }
        if(rightSwitch){
            switchMinItem(a, baseIndex, endIndex, rightSwitch, rightIndex);
        }
        if(leftSwitch){
            switchMinItem(a, baseIndex, endIndex, leftSwitch, leftIndex);
        }
    }

    private static void switchMinItem(int[] a, int baseIndex, int endIndex, boolean isSwitch, int rightIndex) {
        if(isSwitch){
            a[baseIndex] = a[baseIndex] ^ a[rightIndex];
            a[rightIndex] = a[baseIndex] ^ a[rightIndex];
            a[baseIndex] = a[baseIndex] ^ a[rightIndex];
            minHeapSort(a, rightIndex ,endIndex);
        }
    }

    public static void main(String[] args) {
        int a1[] = { 38,65,97,76,13,27,49,27 };
        for(int i = a1.length/2;i>=0;i--){
            maxHeapSort(a1, i, a1.length-1);
        }
        System.out.println(Arrays.toString(a1));

        int a2[] = { 38,65,97,76,13,27,49,27 };
        for(int i = a2.length/2;i>=0;i--){
            minHeapSort(a2, i, a2.length-1);
        }
        System.out.println(Arrays.toString(a2));
    }
}
