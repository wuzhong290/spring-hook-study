package com.demo.javaBase.findk;

import com.demo.javaBase.sort.MaxHeapSort;

import java.util.Arrays;

public class FindkMin {

    static int a[]={49,38,65,97,76,13,27,49};
    //查找一个数组中第k小的数字思路：
    //先从数组中找到k个数字，构建一个大顶堆，
    //堆顶现在就被认为是第k小数
    //数组中其他数据循环和堆顶（b[0]）进行比较，
    //如果堆顶大于当前值,这样的话堆顶就不能认为是第k小数，当前值才是第k小数
    //当前值替换堆顶，在重新建堆
    public static void main(String[] args) {
        int k = 4;
        int b[] = {49,38,65,97};
        int c[] = {76,13,27,49};
        System.out.println(findkMinItem(k, b, c));

    }

    private static int findkMinItem(int k, int[] b, int[] c) {
        //b[0]为堆中最大值，为堆顶
        MaxHeapSort.buildMaxHeap(b,b.length-1);
        System.out.println(Arrays.toString(b));
        for (int i = 0; i < c.length; i++) {
            if(b[0] > c[i]){
                b[0]  = c[i];
                MaxHeapSort.buildMaxHeap(b,k -1);
            }
        }
        System.out.println(Arrays.toString(b));
        return b[0];
    }
}
