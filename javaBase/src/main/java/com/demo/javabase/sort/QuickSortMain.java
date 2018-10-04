package com.demo.javabase.sort;

import java.util.Arrays;

public class QuickSortMain {

    /**
     * 快速排序函数：数组、基准项，开始项索引,结束项索引
     * @param a
     * @param baseItem
     * @param firstIndex
     * @param lastIndex
     */
    private static void quickSortMinToMax(int[] a,int baseItem, int firstIndex, int lastIndex){
        final int preFirstIndex = firstIndex;
        final int preLastIndex = lastIndex;
        while (lastIndex > firstIndex){
            if(baseItem > a[lastIndex]){
                a[firstIndex] = a[lastIndex];
                //将firstIndex指向一个小于a[baseIndex]的项
                while (lastIndex > firstIndex){
                    if(a[++firstIndex] > baseItem){
                        a[lastIndex] = a[firstIndex];
                        break;
                    }
                }
            }
            if(lastIndex > firstIndex){
                --lastIndex;
            }
        }
        if(lastIndex == firstIndex){
            a[firstIndex] = baseItem;
            if(preFirstIndex < firstIndex-1){
                quickSortMinToMax(a, a[preFirstIndex], preFirstIndex, firstIndex-1);
            }
            if(firstIndex+1 < preLastIndex){
                quickSortMinToMax(a, a[firstIndex+1], firstIndex+1, preLastIndex);
            }
        }
    }
    private static void quickSortManToMix(int[] a,int baseItem, int firstIndex, int lastIndex){
        final int preFirstIndex = firstIndex;
        final int preLastIndex = lastIndex;
        while (lastIndex > firstIndex){
            if(baseItem < a[lastIndex]){
                a[firstIndex] = a[lastIndex];
                //将firstIndex指向一个小于a[baseIndex]的项
                while (lastIndex > firstIndex){
                    if(a[++firstIndex] < baseItem){
                        a[lastIndex] = a[firstIndex];
                        break;
                    }
                }
            }
            if(lastIndex > firstIndex){
                --lastIndex;
            }
        }
        if(lastIndex == firstIndex){
            a[firstIndex] = baseItem;
            if(preFirstIndex < firstIndex-1){
                quickSortManToMix(a, a[preFirstIndex], preFirstIndex, firstIndex-1);
            }
            if(firstIndex+1 < preLastIndex){
                quickSortManToMix(a, a[firstIndex+1], firstIndex+1, preLastIndex);
            }
        }
    }
    public static void main(String[] args) {
        int a[] = { 38,65,97,76,13,27,49,27 };
        quickSortMinToMax(a, a[0], 0, a.length-1);
        System.out.println(Arrays.toString(a));

        int a1[] = { 38,65,97,76,13,27,49,27 };
        quickSortManToMix(a1, a1[0], 0, a1.length-1);
        System.out.println(Arrays.toString(a1));
    }
}
