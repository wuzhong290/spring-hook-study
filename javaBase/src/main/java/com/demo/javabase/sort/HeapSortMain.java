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
            switchMaxItem(a, baseIndex, rightIndex, endIndex);
        }
        if(leftSwitch){
            switchMaxItem(a, baseIndex, leftIndex, endIndex);
        }
    }


    private static void switchMaxItem(int[] a, int baseIndex, int switchIndex, int endIndex) {
        a[baseIndex] = a[baseIndex] ^ a[switchIndex];
        a[switchIndex] = a[baseIndex] ^ a[switchIndex];
        a[baseIndex] = a[baseIndex] ^ a[switchIndex];
        //交换后的节点需要重新建堆
        maxHeapSort(a, switchIndex ,endIndex);
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
            switchMinItem(a, baseIndex, rightIndex, endIndex);
        }
        if(leftSwitch){
            switchMinItem(a, baseIndex, leftIndex, endIndex);
        }
    }

    private static void switchMinItem(int[] a, int baseIndex, int switchIndex, int endIndex) {
        a[baseIndex] = a[baseIndex] ^ a[switchIndex];
        a[switchIndex] = a[baseIndex] ^ a[switchIndex];
        a[baseIndex] = a[baseIndex] ^ a[switchIndex];
        //交换后的节点需要重新建堆
        minHeapSort(a, switchIndex ,endIndex);
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
