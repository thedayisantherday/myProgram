package com.example.zxg.myprogram.algorithm;

/**
 * description ：
 * Created by zhuxiaoguang at 14:12 on 2019/9/16
 */
public class SearchRotationSort {

    public static int search(int[] nums, int target) {
        if (nums != null && nums.length > 0) {
            int maxIndex = nums.length - 1;
            int left = 0;
            int right = maxIndex;
            if (nums[0] > nums[maxIndex]) {
                while (left < right) {
                    int mid = (left + right) >> 1;
                    if (nums[mid] > nums[left]) {
                        left = mid;
                    } else {
                        right = mid;
                    }
                }

                if (target == nums[0]) {
                    return 0;
                } else if (target > nums[0]) {
                    left = 0;
                } else {
                    left++;
                    right = maxIndex;
                }
            }
            while (left <= right) {
                int mid = (left + right) >> 1;
                if (nums[mid] == target) {
                    return mid;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public static int[] searchRange(int[] nums, int target) {
        if (nums.length > 0) {
            int left = 0;
            int right = nums.length - 1;
            while (left <= right) {
                int mid = (left + right) >> 1;
                if (nums[mid] == target) {
                    left = searchLeftBound(nums, target, left, mid);
                    right = searchRightBound(nums, target, mid, right);
                    break;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            if (left <= right) {
                return new int[]{left, right};
            }
        }
        return new int[]{-1, -1};
    }

    private static int searchLeftBound(int[] nums, int target, int left, int right) {
        while (left < right) {
            int mid = (left + right) >> 1;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                left = searchLeftBound(nums, target, left, mid);
                break;
            }
        }
        return left;
    }

    private static int searchRightBound(int[] nums, int target, int left, int right) {
        while (left < right) {
            int mid = (left + right + 1) >> 1;
            if (nums[mid] > target) {
                right = mid - 1;
            } else {
                right = searchRightBound(nums, target, mid, right);
                break;
            }
        }
        return right;
    }

}
