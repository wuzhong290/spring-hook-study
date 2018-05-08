package com.demo.javabase.sort;

import java.util.Arrays;

public class MaxHeapSort {
    /**
     * 构建大顶堆
     * 堆的定义
     　　n个元素的序列{k1，k2，…,kn}当且仅当满足下列关系之一时，称之为堆。
     　　情形1：ki <= k2i 且ki <= k2i+1 （最小化堆或小顶堆）
     　　情形2：ki >= k2i 且ki >= k2i+1 （最大化堆或大顶堆）
     一般用数组来表示堆，若根结点存在序号0处，
     i结点的父结点下标就为(i-1)/2。
     i结点的左右子结点下标分别为2*i+1和2*i+2。
     */
    static int a[]={49,38,65,97,76,13,27,49};

    public static void main(String[] args){
        heapSort(a);
        System.out.println(Arrays.toString(a));
    }
    //堆排序函数
    public static void heapSort(int[] a){
        //记录数组长度
        int l = a.length;
        //循环调用建立堆函数建立堆
        for(int i=0;i<l-1;i++){
            //调用建立堆函数
            buildMaxHeap(a,l-1-i);
            //堆建立好后，交换根节点和堆的最后一个节点
            //System.out.println("========="+Arrays.toString(a));
            //每建一次堆就是就找到了最大数（第一位数），
            // 把第一位数和最后一位交换一下，
            // 下一次建堆不包含最后一位
            swap(a,0,l-1-i);
            //System.out.println(Arrays.toString(a));
        }
    }
    //交换元素的函数
    public static void swap(int[] a,int i,int j){
//        int tmp = a[i];
//        a[i] = a[j];
//        a[j] = tmp;
        a[i] = a[i]^a[j];
        a[j] = a[i]^a[j];
        a[i] = a[i]^a[j];
    }

    /**
     * 建立堆函数，在数组a中，从0到lastIndex建立堆
     */
    public static void buildMaxHeap(int[] a,int lastIndex){
        //从lastIndex节点（最后一个节点）的父节点开始循环
        //(lastIndex-1)/2表示最后一个非叶子节点
        for(int j=(lastIndex-1)/2;j>=0;j--){
            //k保存正在判断的节点
            int k = j;
            nodeBuildHeap(a, lastIndex, k);
        }
    }

    private static void nodeBuildHeap(int[] a, int lastIndex, int k) {
        //如果当前k节点的子节点存在
        while(2*k+1 <= lastIndex){
            //k节点的左子节点索引
            int biggerIndex = 2*k+1;
            //表示k节点存在右节点
            if(biggerIndex < lastIndex && a[biggerIndex] < a[biggerIndex+1]){
                biggerIndex++;//biggerIndex总是记录较大子节点的索引
            }
            //如果k节点的值小于较大子节点的值，则交换它们的值，并将biggerIndex赋值给k，开始下一次循环
            if(a[k] < a[biggerIndex]){
                //保证k节点的值大于其子节点的值,符合堆定义
                swap(a,k,biggerIndex);
                //赋值
                k = biggerIndex;
            }else{
                break;
            }
        }
    }
}