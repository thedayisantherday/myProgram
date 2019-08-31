package com.example.zxg.myprogram.test;

import android.util.Log;

import com.example.zxg.myprogram.algorithm.MergeSort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50;
    static final int ADJUSTMENT_PERIOD = 1000;
    private static int tts = 10;
    private int tt = 10;

    static Test<Child>[] tests;

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


        Test test = new Test<Child>(new Child());
        Test1 test1 = new Test1(new Child());
        test.get();
        test1.get();

        BankTellerSimulation bankTellerSimulation = new BankTellerSimulation();
        Test00 test00 = bankTellerSimulation.new Test00();
        test00.test();

        Test<Integer> test11 = new Test<Integer>();
//        tests = (Test<Child>[]) new Object[3];

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
        swapPairs(l2);

        removeDuplicates(new int[]{1, 1, 2});
        uniquePaths(10, 10);
        uniquePaths(1, 2);
        uniquePaths(3, 1);
        uniquePaths(3, 2);
        uniquePaths(36, 7);
        uniquePaths(3, 3);
        uniquePaths(4, 4);

        uniquePathsWithObstacles(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}});
        longestIncreasingPath(new int[][]{{9,9,4},{6,6,8},{2,1,1}});


        Log.i("convert String", "onClick: 3 convertStr = " + convert("PAYPALISHIRING", 3));
        Log.i("convert String", "onClick: 4 convertStr = " + convert("PAYPALISHIRING", 4));
    }

    private static final int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private static int m, n;
    public static int longestIncreasingPath(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        // padding the matrix with zero as boundaries
        // assuming all positive integer, otherwise use INT_MIN as boundaries
        int[][] matrix = new int[m + 2][n + 2];
        for (int i = 0; i < m; ++i)
            System.arraycopy(grid[i], 0, matrix[i + 1], 1, n);

        // calculate outdegrees
        int[][] outdegree = new int[m + 2][n + 2];
        for (int i = 1; i <= m; ++i)
            for (int j = 1; j <= n; ++j)
                for (int[] d: dir)
                    if (matrix[i][j] < matrix[i + d[0]][j + d[1]])
                        outdegree[i][j]++;

        // find leaves who have zero out degree as the initial level
        List<int[]> leaves = new ArrayList<>();
        for (int i = 1; i <= m; ++i)
            for (int j = 1; j <= n; ++j)
                if (outdegree[i][j] == 0) leaves.add(new int[]{i, j});

        // remove leaves level by level in topological order
        int height = 0;
        while (!leaves.isEmpty()) {
            height++;
            List<int[]> newLeaves = new ArrayList<>();
            for (int[] node : leaves) {
                for (int[] d:dir) {
                    int x = node[0] + d[0], y = node[1] + d[1];
                    if (matrix[node[0]][node[1]] > matrix[x][y])
                        if (--outdegree[x][y] == 0)
                            newLeaves.add(new int[]{x, y});
                }
            }
            leaves = newLeaves;
        }
        return height;
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

    public static ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode temp = head;
        ListNode index;

        head = head.next;
        index = head.next;
        head.next = temp;
        temp.next = swapPairs(index);
        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int x) { val = x; }
    }

    public static int removeDuplicates(int[] nums) {
        Set set = new HashSet();
        if (nums == null || nums.length == 0) return 0;
        int result = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i-1]) {
                if (i != result) {
                    nums[result] = nums[i];
                }
                result++;
            }
        }
        return result;
    }

    public static int uniquePaths(int m, int n) {
        /*int[] all = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    all[j] = 1;
                } else {
                    all[j] = all[j] + all[j - 1];
                }
            }
        }
        return all[n - 1];*/
        double result = 1;
        for (int i = m-1; i >= 1; i--) {
            result *= (m+n-1-i)/(double) i;
        }
        return (int)Math.round(result);
    }

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length <= 0 || obstacleGrid[0].length <= 0) return 0;

        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if (i == 0 || j == 0) {
                    if (obstacleGrid[i][j] == 1 || (i > 0 && obstacleGrid[i-1][j] == 0) || (j > 0 && obstacleGrid[i][j-1] == 0)) {
                        obstacleGrid[i][j] = 0;
                    } else {
                        obstacleGrid[i][j] = 1;
                    }
                } else {
                    if (obstacleGrid[i][j] == 1) {
                        obstacleGrid[i][j] = 0;
                    } else {
                        obstacleGrid[i][j] = obstacleGrid[i - 1][j] + obstacleGrid[i][j - 1];
                    }
                }
            }
        }
        return obstacleGrid[obstacleGrid.length-1][obstacleGrid[0].length-1];
    }


    public static class Test<T> {
        public T entity;
        public T[] entitys;
        @SuppressWarnings("unChecked")
        public Test() {
            entitys = (T[])new Object[10];
        }
        public Test(T t) {
            entity = t;
        }

        public T get() {
            return entity;
        }
    }

    public static class Test1 {
        public Base entity;
        public Test1(Base t) {
            entity = t;
        }

        public Base get() {
            BankTellerSimulation.tts = 1;
            return entity;
        }
    }

    public static class Child extends Base{
        public void func(){}
    }

    public static class Base {

    }

    public class Test00 {
        public Test00() {}
        public void test(){
            int xx = tt +2;
        }
    }

    /**
     *
     * @param str
     * @param start
     */
    private void printStr (String str, int start) {
        if (str == null || str.length() == 0) {
            return;
        }
        if (str.length() == start) {
            System.out.println(str);
        }
        for (int i = start-1; i < str.length(); i++) {
            String temp = str.substring(0, start-1) + str.charAt(i);
            if (start < str.length()) {
                temp = temp + str.replace(String.valueOf(str.charAt(i)), "").substring(start-1);
            }
            printStr(temp, start+1);
        }
    }

    private void printStr1 (String str, int start) {
        if (str == null || str.length() == 0) {
            return;
        }
        if (str.length() == start) {
            System.out.println(str);
        }
        char[] temp = str.toCharArray();
        for (int i = start; i < temp.length; i++) {
            swipt(temp, start, i);
            printStr1(new String(temp), start + 1);
        }
    }

    private static void swipt(char[] chars, int start, int index) {
        if (start >= index) {
            return;
        }
        char temp = chars[start];
        chars[start] = chars[index];
        chars[index] = temp;
    }

    public static String convert(String s, int numRows) {
        if (s == null || numRows <= 1) {
            return s;
        }

        StringBuilder[] chars = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            chars[i] = new StringBuilder();
        }
        boolean isAdd = true;
        int row = 0;
        for (int i = 0; i < s.length(); i++) {
            chars[row].append(s.charAt(i));
            if (row == 0) {
                isAdd = true;
            } else if (row == numRows-1) {
                isAdd = false;
            }

            if (isAdd) {
                row++;
            } else {
                row--;
            }
        }

        for (int i = 1; i < numRows; i++) {
            chars[0].append(chars[i]);
        }
        return chars[0].toString();
    }
}
