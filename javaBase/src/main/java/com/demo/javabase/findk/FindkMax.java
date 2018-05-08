package com.demo.javabase.findk;

import com.demo.javabase.sort.MinHeapSort;

import java.util.Arrays;

public class FindkMax {
    static int a[]={49,38,65,97,76,13,27,49};
    //查找一个数组中第k大的数字思路：
    //先从数组中找到k个数字，构建一个小顶堆，
    //堆顶现在就被认为是第k大数
    //数组中其他数据循环和堆顶（b[0]）进行比较，
    //如果堆顶小于当前值，这样的话堆顶就不能认为是第k大数，当前值才是第k大数
    //当前值替换堆顶，在重新建堆
    public static void main(String[] args) {
        int k = 3;
        int b[] = {49,38,65};
        int c[] = {97,76,13,27,49};
        //b[0]为最小值,为堆顶
        MinHeapSort.buildMinHeap(b,b.length-1);
        System.out.println(Arrays.toString(b));
        for (int i = 0; i < c.length; i++) {
            if(b[0] < c[i]){
                b[0]  = c[i];
                MinHeapSort.buildMinHeap(b,k -1);
            }
        }
        System.out.println(Arrays.toString(b));
    }
}
