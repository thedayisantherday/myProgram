package com.example.zxg.myprogram.algorithm;

public class QuickSort {

    public static void quickSort(int[] arrays, int low, int height) {
        if (arrays == null || arrays.length <= 1 || low >= height) return;

        int left = low;
        int right = height;
        int base = arrays[low];

        while (left < right) {
            while (left < right && arrays[right] >= base) {
                right--;
            }
            if (left < right) {
                arrays[left] = arrays[right];
                left++;
            }

            while (left < right && arrays[left] <= base) {
                left++;
            }
            if (left < right) {
                arrays[right] = arrays[left];
                right--;
            }
        }

        arrays[left] = base;
        quickSort(arrays, low, left-1);
        quickSort(arrays, left+1, height);
    }
}
