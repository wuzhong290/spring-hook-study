package com.demo.javabase.sort;

import java.util.Arrays;

public class InsertSortMain {
    /**
     * 插入排序：就是往已经排好序的数组插入，和邻近前一个进行比较
     * 插入排序函数：数组、开始项beginIndex，最后位索引endIndex
     * 插入排序函数：
     *      第一步：beginIndex 和其前面邻居比较，如果小于邻居，进行替换，依次向前替换，直到大于前面邻居
     *      第二步：已选项索引selectIndex 如果小于 endIndex，向前推进一位，递归执行
     * @param a  待排序的数组
     * @param beginIndex
     * @param endIndex
     */
    private static void insertSortMinToMax(int a[],int beginIndex, int endIndex){
        int index = beginIndex -1;
        while (index >= 0){
            if(a[index] > a[index+1]){
                a[index] =a[index]^a[index+1];
                a[index+1] =a[index]^a[index+1];
                a[index] =a[index]^a[index+1];
            }
            --index;
        }
        if(beginIndex < endIndex){
            insertSortMinToMax(a, ++beginIndex, endIndex);
        }
    }

    private static void insertSortMaxToMin(int a[],int beginIndex, int endIndex){
        int index = beginIndex -1;
        while (index >= 0){
            if(a[index] < a[index+1]){
                a[index] =a[index]^a[index+1];
                a[index+1] =a[index]^a[index+1];
                a[index] =a[index]^a[index+1];
            }
            --index;
        }
        if(beginIndex < endIndex){
            insertSortMaxToMin(a, ++beginIndex, endIndex);
        }
    }

    public static void main(String[] args) {
        int a1[] = { 38,65,97,76,13,27,49,27 };
        insertSortMinToMax(a1, 1, a1.length-1);
        System.out.println(Arrays.toString(a1));

        int a[] = { 38,65,97,76,13,27,49,27 };
        insertSortMaxToMin(a, 1, a.length-1);
        System.out.println(Arrays.toString(a));
    }
}
