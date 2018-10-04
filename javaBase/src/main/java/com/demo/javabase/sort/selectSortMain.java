package com.demo.javabase.sort;

import java.util.Arrays;

public class selectSortMain {

    /**
     * 选择排序：每一次排序从未进行排序的数组中选出最小的数字。
     * 选择排序函数：数组、已选项索引、最后位索引
     * 选择排序函数：
     *      第一步：已选项作为最小的数字，其后面项和它比较，小于已选项的，和他进行替换
     *      第二步：已选项索引selectIndex 如果小于 endIndex，向前推进一位，递归执行
     * @param a
     * @param selectIndex
     * @param endIndex
     */
    private static void selectSortMinToMax(int[] a, int selectIndex , int endIndex){
        int index = selectIndex +1;
        while (index <= endIndex){
            if(a[selectIndex] > a[index]){
                a[selectIndex] = a[selectIndex] ^ a[index];
                a[index] = a[selectIndex] ^ a[index];
                a[selectIndex] = a[selectIndex] ^ a[index];
            }
            ++index;
        }
        if(selectIndex < endIndex){
            selectSortMinToMax(a, ++selectIndex, endIndex);
        }
    }

    private static void selectSortMaxToMin(int[] a, int selectIndex , int endIndex){
        int index = selectIndex +1;
        while (index <= endIndex){
            if(a[selectIndex] < a[index]){
                a[selectIndex] = a[selectIndex] ^ a[index];
                a[index] = a[selectIndex] ^ a[index];
                a[selectIndex] = a[selectIndex] ^ a[index];
            }
            ++index;
        }
        if(selectIndex < endIndex){
            selectSortMaxToMin(a, ++selectIndex, endIndex);
        }
    }
    public static void main(String[] args) {
        int a[] = { 38,65,97,76,13,27,49,27 };
        selectSortMinToMax(a, 0, a.length-1);
        System.out.println(Arrays.toString(a));

        int a1[] = { 38,65,97,76,13,27,49,27 };
        selectSortMaxToMin(a1, 0, a1.length-1);
        System.out.println(Arrays.toString(a1));
    }
}
