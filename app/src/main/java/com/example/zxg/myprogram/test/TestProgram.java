package com.example.zxg.myprogram.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by zxg on 2019/2/24.
 */

public class TestProgram {

    public static void main(String[] args) {

        myAtoi("-");

        isPalindrome(121);

        Integer testInteger = 30;
        int testInt = 30;

        int[] height = {1,9,6,2,5,4,8,9,7};
        maxArea(height);

        String str9 = intToRoman(9);
        String str11 = intToRoman(11);
        String str874 = intToRoman(874);
        String str2458 = intToRoman(2458);

        longestCommonPrefix(new String[]{"aa", "a", "aaa"});

        int[] nums = new int[]{-1,0,1,2,-1,-4};
        mergeSort(nums, 0, nums.length-1);
        List<List<Integer>> resultList = threeSum(nums);

        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        removeNthFromEnd(head, 1);

//        reverseKGroup(head, 2);

        isValid("()");
        isValid("()[]{}");
        isValid("(]");
        isValid("([)]");
        isValid("([)]");
        isValid("{[]}");

        generateParenthesis(3);

        /*longestValidParentheses(")()()()");
        longestValidParentheses(")(()()()");
        longestValidParentheses(")())()()");
        longestValidParentheses("()(()");
        longestValidParentheses("(()");*/
        longestValidParentheses(")(");

        maxProfit(new int[]{7,1,5,3,6,4});
        maxProfit(new int[]{7,6,4,3,1});

        longestIncreasingPath(new int[][] {{9,9,4}, {6,6,8}, {2,1,1}});
        longestIncreasingPath(new int[][] {{3,4,5}, {3,2,6}, {2,2,1}});

        Test<Base> test = new Test<>();
        test.setTest(new Base());
        test.getTest().getValue();

        Test test0 = new Test();
        test.setTest(new Base());
        test.getTest().getValue();

        Test1 test1 = new Test1();
        test1.setTest(new Base());
    }

    public static class Test<T> {
        private T test;
        public void setTest(T t) {
            test = t;
        }
        public T getTest() {
            String switchStr = "123";
            int swithchInt = 1;
            switch(switchStr) {
            case "12":
                break;
            }
            return test;
        }
    }

    public static class Test1 {
        private Object test;
        public void setTest(Object t) {
            test = t;
        }
        public Object getTest() {
            return test;
        }
    }

    public static class Base{
        public int value;
        public int getValue() {
            return value;
        }
    }

    private static int[][] offsets = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private static int row, col;
    public static int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int result = 0;
        row = matrix.length;
        col = matrix[0].length;
        int[][] cache = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result = Math.max(result, maxLength(matrix, i, j, cache));
            }
        }
        return result;
    }

    private static int maxLength(int[][] matrix, int i, int j, int[][] cache) {
        if(cache[i][j] != 0) return cache[i][j];
        for(int[] offset : offsets) {
            int x = i+offset[0], y = j + offset[1];
            if (x >= 0 && x < row && y >= 0 && y < col && matrix[i][j] < matrix[x][y]) {
                cache[i][j] = Math.max(cache[i][j], maxLength(matrix, x, y, cache));
            }
        }
        return ++cache[i][j];
    }

    public static int myAtoi(String str) {
        if(str == null){
            return 0;
        }

        boolean isPositive = true;
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (nums.size() == 0) {
                if (str.charAt(i) == 45) {
                    isPositive = false;
                    nums.add(0);
                } else if (str.charAt(i) == 43) {
                    isPositive = true;
                    nums.add(0);
                } else if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    nums.add(str.charAt(i) - 48);
                } else if (str.charAt(i) != 32) {
                    return 0;
                }
            } else {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    if (nums.get(0) <= 0) {
                        nums.remove(0);
                    }
                    nums.add(str.charAt(i) - 48);
                } else {
                    break;
                }
            }
        }

        return changeValue(nums, isPositive);
    }

    private static int changeValue(List<Integer> nums, boolean isPositive) {
        if (nums.size() > 10) {
            if (isPositive) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        } else {
            long result = 0;
            for (int i = 0; i < nums.size(); i++) {
                result = result * 10 + nums.get(i);
            }
            if (result > Integer.MAX_VALUE) {
                if (isPositive) {
                    return Integer.MAX_VALUE;
                } else {
                    return Integer.MIN_VALUE;
                }
            } else {
                return (int) result * (isPositive ? 1 : -1);
            }
        }
    }

    public static boolean isPalindrome(int x) {
        if(x < 0) {
            return false;
        }

        int result = 0;
        while(x > 0) {
            result = result * 10 + x % 10;
            x = x/10;
        }

        return result == x;
    }

    public static int maxArea(int[] height) {
        int max = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            if (height[left] > height[right]) {
                max = Math.max(max, (right - left) * height[right]);
                right--;
            } else {
                max = Math.max(max, (right - left) * height[left]);
                left++;
            }
        }
        return max;
    }

    public static String intToRoman(int num) {
        int temp = 10000;
        StringBuilder result = new StringBuilder();
        String[] romanStr = {"I", "V", "X", "L", "C", "D", "M"};
        for (int i = 3; i >= 0; i--) {
            temp /= 10;
            int remainder = num / temp % 10;
            switch (remainder) {
                case 3:
                    result.append(romanStr[i<<1]);
                case 2:
                    result.append(romanStr[i<<1]);
                case 1:
                    result.append(romanStr[i<<1]);
                    break;
                case 4:
                    result.append(romanStr[i<<1]);
                case 5:
                    result.append(romanStr[(i<<1) + 1]);
                    break;
                case 8:
                    result.append(romanStr[i<<1]);
                case 7:
                    result.append(romanStr[i<<1]);
                case 6:
                    result.append(romanStr[i<<1]);
                    result.insert(result.length() - (remainder - 5), romanStr[(i<<1) + 1]);
                    break;
                case 9:
                    result.append(romanStr[i<<1]).append(romanStr[(i<<1) + 2]);
                    break;
            }
        }
        return result.toString();
    }

    public static String longestCommonPrefix(String[] strs) {
        if (null == strs || strs.length == 0) return "";

        String minStr = strs[0];
        for (int i = 1; i < strs.length; i++) {
            String tempStr = strs[i];
            int num = minStr.length();
            if (num > tempStr.length()) {
                num = tempStr.length();
                tempStr = minStr;
                minStr = strs[i];
            }
            for (int j = 0; j < num; j++) {
                if (tempStr.charAt(j) != minStr.charAt(j)) {
                    minStr = minStr.substring(0, j);
                    break;
                }
            }
        }

        return minStr;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) return new ArrayList<>();
        mergeSort(nums, 0, nums.length-1);
        Arrays.sort(nums);
        List<List<Integer>> resultList = new ArrayList<>();
        int length = nums.length;
        for (int i = 0; i < length - 2; i++) {
            if (i > 0 && nums[i] == nums[i-1]) continue;
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[right] > -1 * nums[i]) {
                    right--;
                } else if (nums[left] + nums[right] < -1 * nums[i]) {
                    left++;
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left++]);
                    list.add(nums[right--]);
                    if (resultList.size() == 0 || !resultList.get(resultList.size() - 1).equals(list)) {
                        resultList.add(list);
                    }
                }
            }
        }
        return resultList;
    }

    public static void mergeSort(int[] nums, int left, int right) {
        if (null == nums) return;

        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(nums, left, mid);
            mergeSort(nums, mid + 1, right);
            merge(nums, left, mid, right);
        }
    }

    public static void merge(int[] nums, int left, int mid, int right) {
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

        while (m <= mid) {
            temp[k++] = nums[m++];
        }

        while (n <= right) {
            temp[k++] = nums[n++];
        }

        k = 0;
        while (left <= right) {
            nums[left++] = temp[k++];
        }
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode[] listNodes = new ListNode[n+1];
        int temp = 0;
        for(ListNode node = head; node != null; node = node.next, temp = (++temp) % (n+1)) {
            listNodes[temp] = node;
        }


        if (listNodes[temp] == null) {
            head = listNodes[(temp + 2) % (n + 1)];
        } else if (listNodes[temp] == listNodes[(temp + 2) % (n + 1)]) {
            listNodes[temp].next = null;
        } else {
            listNodes[temp].next = listNodes[(temp + 2) % (n + 1)];
        }
        return head;
    }

    public static boolean isValid(String s) {
        Stack stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case ')':
                    if (stack.size() == 0 || !stack.pop().equals('(')) {
                        return false;
                    }
                    break;
                case '}':
                    if (stack.size() == 0 || !stack.pop().equals('{')) {
                        return false;
                    }
                    break;
                case ']':
                    if (stack.size() == 0 || !stack.pop().equals('[')) {
                        return false;
                    }
                    break;
                case '(':
                case '{':
                case '[':
                default:
                    stack.push(s.charAt(i));
                    break;
            }
        }

        return stack.size() == 0;
    }

    public static List<String> generateParenthesis(int n) {
        List list = new ArrayList();
        if (n > 0) {
            list.add(generateStr(n));
            List tp = generateParenthesis(n - 1);
            for (int i = 1; i < tp.size(); i++) {
                list.add("(" + tp.get(i) + ")");
            }
            for (int i = n - 1; i > 0; i--) {
                List temp = generateParenthesis(n - i);
                for (int j = 0; j < temp.size(); j++) {
                    list.add(generateStr(i) + temp.get(j));
                }
            }
        }

        return list;
    }

    public static String generateStr(int num) {
        StringBuilder stringBuilder = new StringBuilder();
        String left = "(";
        String right = ")";
        for (int i = 0; i < 2*num; i++) {
            if(i < num) {
                stringBuilder.append(left);
            } else {
                stringBuilder.append(right);
            }
        }
        return stringBuilder.toString();
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode[] tempNodes = new ListNode[k+1];
        tempNodes[0] = head;
        for (int i = 1; i < k+1; i++) {
            if (tempNodes[i-1] == null) {
                return head;
            }
            tempNodes[i] = tempNodes[i-1].next;
        }

        for (int j = k-1; j > 0; j--) {
            tempNodes[j].next = tempNodes[j-1];
        }
        tempNodes[0].next = reverseKGroup(tempNodes[k], k);

        return tempNodes[k-1];
    }

    public static int longestValidParentheses(String s) {
        /*int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                    stack.push(i);
                    break;
                default:
                    stack.pop();
                    if (stack.empty()) {
                        stack.push(i);
                    } else {
                        maxans = Math.max(maxans, i - stack.peek());
                    }
            }
        }
        return maxans;*/

        int left = 0, right = 0, maxlength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * right);
            } else if (right >= left) {
                left = right = 0;
            }
        }

        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * left);
            } else if (left >= right) {
                left = right = 0;
            }
        }
        return maxlength;
    }

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;

        int min = prices[0];
        prices[0] = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < min) {
                min = prices[i];
            } else if (prices[i] - min > prices[0]) {
                prices[0] = prices[i] - min;
            }
        }
        return prices[0];
    }
}
