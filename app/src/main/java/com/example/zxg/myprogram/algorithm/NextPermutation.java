package com.example.zxg.myprogram.algorithm;

/**
 * description ：
 * Created by zhuxiaoguang at 23:41 on 2019/9/10
 */
public class NextPermutation {

    public static void nextPermutation(int[] nums) {
        if (nums == null) return;

        int left = nums.length - 2;
        int right = nums.length - 1;
        while (left >= 0 && nums[left + 1] <= nums[left]) {
            left--;
        }
        if (left >= 0) {
            while (nums[right] <= nums[left]) {
                right--;
            }
            swap(nums, left, right);
        }

        if (right >= 0) {
            left = left + 1;
        }
        for (right = nums.length - 1; left < right; left++, right--) {
            swap(nums, left, right);
        }
    }

    private static void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }

}
