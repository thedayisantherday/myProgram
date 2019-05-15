package com.example.zxg.myprogram.test;

import com.example.zxg.myprogram.algorithm.MergeSort;

import java.util.ArrayList;
import java.util.List;

public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50;
    static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws Exception {
        /*ExecutorService exec = Executors.newCachedThreadPool();

        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        exec.execute(new CustomerGenerator(customers));
        exec.execute(new TellerManager(exec, customers, ADJUSTMENT_PERIOD));

        if (args.length > 0) {
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        } else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }

        exec.shutdown();*/

        int[] nums1 = {1, 9, 11};
        int[] nums2 = {2, 13};
        findMedianSortedArrays(nums1, nums2);

        myAtoi("-   234");

        isMatch("aava", "a*va");

        String str3 = intToRoman(3);
        String str9 = intToRoman(9);
        String str11 = intToRoman(11);
        String str110 = intToRoman(110);
        String str847 = intToRoman(847);
        String str2583 = intToRoman(2583);

        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
//        List<List<Integer>> resultList = threeSum(nums);

        MergeSort.mergeSort(nums, 0, nums.length - 1);
        MergeSort.mergeSort(nums, 0, nums.length - 1);

        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(4);
        l2.next = new ListNode(5);
        l2.next.next = new ListNode(6);
        l2.next.next.next = new ListNode(7);
        ListNode[] list = new ListNode[]{l1, l2};
        mergeKLists(list);
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        if(n > m)   //保证数组1一定最短
            return findMedianSortedArrays(nums2,nums1);
        int L1 = 0,L2 = 0,R1 = 0,R2 = 0,c1 = 0,c2 = 0,lo = 0, hi = 2*n;  //我们目前是虚拟加了'#'所以数组1是2*n长度
        while(lo <= hi)   //二分
        {
            c1 = (lo+hi)/2;  //c1是二分的结果
            c2 = m+n- c1;
            L1 = (c1 == 0)?Integer.MIN_VALUE:nums1[(c1-1)/2];   //map to original element
            R1 = (c1 == 2*n)?nums1[n-1]:nums1[c1/2];
            L2 = (c2 == 0)?nums2[0]:nums2[(c2-1)/2];
            R2 = (c2 == 2*m)?nums2[m-1]:nums2[c2/2];

            if(L1 > R2)
                hi = c1-1;
            else if(L2 > R1)
                lo = c1+1;
            else
                break;
        }
        return (Math.max(L1,L2)+ Math.min(R1,R2))/2.0;
    }

    public static int myAtoi(String str) {
        if(str == null || str.length() == 0 || str.replaceAll(" ", "").length() == 0){
            return 0;
        }

        int isPositive = 2;
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (nums.size() == 0) {
                if (str.charAt(i) == 45) {
                    if (isPositive != 2) {
                        return 0;
                    }
                    isPositive = 0;
                } else if (str.charAt(i) == 43) {
                    if (isPositive != 2) {
                        return 0;
                    }
                    isPositive = 1;
                } else if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    nums.add(str.charAt(i) - 48);
                } else if (str.charAt(i) != 32) {
                    return 0;
                } else {
                    if (isPositive != 2) {
                        return 0;
                    }
                }
            } else {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    if (nums.get(0) == 0) {
                        nums.remove(0);
                    }
                    nums.add(str.charAt(i) - 48);
                } else {
                    break;
                }
            }
        }

        if (nums.size() == 0) {
            return 0;
        } else {
            return changeValue(nums, isPositive > 0);
        }
    }

    private static int changeValue(ArrayList<Integer> nums, boolean isPositive) {
        int[] value = {2, 1, 4, 7, 4, 8, 3, 6, 4, 7};
        int size = nums.size();
        if (size > 10) {
            if (isPositive) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        } else if (size < 10) {
            return getResult(nums, isPositive);
        } else {
            int dValue = 0;
            for (int i = 0; i < 10; i++) {
                if (dValue > 0) {
                    if (isPositive) {
                        return Integer.MAX_VALUE;
                    } else {
                        return Integer.MIN_VALUE;
                    }
                } else if (dValue < 0) {
                    return getResult(nums, isPositive);
                } else {
                    if (i == 9) {
                        if (isPositive) {
                            if (nums.get(i) >= 7) {
                                return Integer.MAX_VALUE;
                            } else {
                                return getResult(nums, isPositive);
                            }
                        } else {
                            if (nums.get(i) >= 8) {
                                return Integer.MIN_VALUE;
                            } else {
                                return getResult(nums, isPositive);
                            }
                        }
                    }
                }
                dValue = nums.get(i) - value[i];
            }
        }
        return getResult(nums, isPositive);
    }

    private static int getResult(ArrayList<Integer> nums, boolean isPositive) {
        int result = 0;
        for (int i = 0; i < nums.size(); i++) {
            result = result * 10 + nums.get(i);
        }
        return result * (isPositive ? 1 : -1);
    }

    private static boolean isMatch(String text, String pattern) {
        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
        dp[text.length()][pattern.length()] = true;

        for (int i = text.length(); i >= 0; i--){
            for (int j = pattern.length() - 1; j >= 0; j--){
                boolean first_match = (i < text.length() &&
                        (pattern.charAt(j) == text.charAt(i) ||
                                pattern.charAt(j) == '.'));
                if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*'){
                    dp[i][j] = dp[i][j+2] || first_match && dp[i+1][j];
                } else {
                    dp[i][j] = first_match && dp[i+1][j+1];
                }
            }
        }
        return dp[0][0];
    }

    public static String intToRoman(int num) {
        String result = "";
        String[] ramanStr = {"I", "V", "X", "L", "C", "D", "M"};
        for (int i = 0; i < 3; i++) {
            int remainder = num % 10;
            num /= 10;
            switch (remainder) {
                case 0:
                    break;
                case 1:
                case 2:
                case 3:
                    for (int j = 0; j < remainder; j++) {
                        result = ramanStr[i<<1] + result;
                    }
                    break;
                case 4:
                    result = ramanStr[i<<1] + ramanStr[(i<<1) + 1] + result;
                    break;
                case 5:
                    result = ramanStr[(i<<1) + 1] + result;
                    break;
                case 6:
                case 7:
                case 8:
                    for (int j = 0; j < remainder-5; j++) {
                        result = ramanStr[i<<1] + result;
                    }
                    result = ramanStr[(i<<1) + 1] + result;
                    break;
                case 9:
                    result = ramanStr[i<<1] + ramanStr[(i<<1) + 2] + result;
                    break;
            }
        }
        for (int i = 0; i < num; i++) {
            result = ramanStr[6] + result;
        }
        return result;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) return null;
        MergeSort.mergeSort(nums, 0, nums.length-1);

        List<List<Integer>> resultList = new ArrayList<>();
        int length = nums.length;
        for (int i = 0; i < length - 2; i++) {
            for (int j = i+1; j < length - 1; j++) {
                int num = -1 * (nums[i] + nums[j]);
                int left = j + 1;
                int right = length - 1;
                while (left <= right) {
                    int mid = (left + right) / 2;
                    if (nums[mid] > num) {
                        right = mid - 1;
                    } else if (nums[mid] < num) {
                        left = mid + 1;
                    } else {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[mid]);
                        resultList.add(list);
                        break;
                    }
                }
            }
        }
        return resultList;
    }

    private static int[] mergeKLists(List<int[]> lists) {
        int[] result = null;
        if (!lists.isEmpty()) {
            result = mergeLists(lists, 0, lists.size() - 1);
        }
        return result;
    }

    private static int[] mergeLists(List<int[]> lists, int start, int end) {
        switch (end - start) {
            case 0:
                return lists.get(start);
            default:
                int[] result = merge(mergeLists(lists, start, start + (end - start)/2), mergeLists(lists, start + (end - start)/2 + 1, end));
                return result;
        }
    }

    private static int[] merge(int[] l1, int[] l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else {
            int[] result = new int[l1.length + l2.length];
            int num = 0, num1 = 0, num2 = 0;
            while (num1 < l1.length && num2 < l2.length) {
                if (l1[num1] < l2[num2]) {
                    result[num++] = l1[num1++];
                } else {
                    result[num++] = l2[num2++];
                }
            }
            while (num1 < l1.length) {
                result[num++] = l1[num1++];
            }
            while (num2 < l2.length) {
                result[num++] = l2[num2++];
            }
            return result;
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        } else{
            ListNode node = mergeLists(lists, 0, lists.length - 1);
            return node;
        }
    }

    private static ListNode mergeLists(ListNode[] lists, int start, int end) {
        switch (end - start) {
            case 0:
                return lists[start];
            default:
                ListNode node = merge(mergeLists(lists, start, start + (end - start)/2), mergeLists(lists, start + (end - start)/2 + 1, end));
                return node;
        }
    }

    private static ListNode merge(ListNode l1, ListNode l2) {
        ListNode num = new ListNode(0), num1 = l1, num2 = l2;
        ListNode result = num;
        while (num1 != null && num2 != null) {
            if (num1.val < num2.val) {
                num.next = num1;
                num1 = num1.next;
            } else {
                num.next = num2;
                num2 = num2.next;
            }
            num = num.next;
        }
        while (num1 != null) {
            num.next = num1;
            num1 = num1.next;
            num = num.next;
        }
        while (num2 != null) {
            num.next = num2;
            num2 = num2.next;
            num = num.next;
        }
        return result.next;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
