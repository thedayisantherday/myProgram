package com.example.zxg.myprogram.algorithm;

public class MergeSort {

    public static void mergeSort(int[] nums, int left, int right) {
        if (null == nums) return;

        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(nums, left, mid);
            mergeSort(nums, mid + 1, right);
            merge(nums, left, mid, right);
        }
    }

    private static void merge(int[] nums, int left, int mid, int right) {
        int m = left;
        int n = mid+1;
        int k = 0;
        int[] temp = new int[right - left + 1];

        while (m <= mid && n <= right) {
            if(nums[m] <= nums[n]) {
                temp[k++] = nums[m++];
            } else {
                temp[k++] = nums[n++];
            }
        }

        while (m < mid) {
            temp[k++] = nums[m++];
        }

        while (n < right) {
            temp[k++] = nums[n++];
        }

        k = 0;
        while (left <= right) {
            nums[left++] = temp[k++];
        }
    }
}
