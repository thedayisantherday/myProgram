package com.example.zxg.myprogram.algorithm;

import java.util.Arrays;

public class AlgorithmMain {
    public static void main(String[] args) throws Exception {
        int[] arrays = new int[]{15, 3, 6, 49, 8, 38, 65, 2, 97, 76, 13, 27, 7, 14, 19, 1};
        QuickSort.quickSort(arrays, 0, arrays.length-1);
        int[] result = arrays;

        PhoneLetterCombination.letterCombinations("279");

        MergeTwoLinkList.ListNode l1 = new MergeTwoLinkList.ListNode(1);
        l1.next = new MergeTwoLinkList.ListNode(2);
        l1.next.next = new MergeTwoLinkList.ListNode(4);
        MergeTwoLinkList.ListNode l2 = new MergeTwoLinkList.ListNode(1);
        l2.next = new MergeTwoLinkList.ListNode(3);
        l2.next.next = new MergeTwoLinkList.ListNode(4);
        MergeTwoLinkList.mergeTwoLists(l1, l2);
        NextPermutation.nextPermutation(new int[]{3, 2, 1});
        NextPermutation.nextPermutation(new int[]{1, 2, 3});
        NextPermutation.nextPermutation(new int[]{1, 1, 5});

        SearchRotationSort.search(new int[] {4, 5, 6, 7, 0, 1, 2}, 0);
        SearchRotationSort.search(new int[] {4, 5, 6, 7, 0, 1, 2}, 7);
        SearchRotationSort.search(new int[] {4, 5, 6, 7, 0, 1, 2}, 3);
        SearchRotationSort.search(new int[] {4, 5, 6, 7, 0, 1, 2}, 4);
        SearchRotationSort.search(new int[] {4, 5, 6, 7, 0, 1, 2}, 2);

        SearchRotationSort.searchRange(new int[] {8, 8}, 8);
        SearchRotationSort.searchRange(new int[] {0,0,0,0,1,2,3,3,4,5,6,6,7,8,8,8,9,9,10,10,11,11}, 0);
        SearchRotationSort.searchRange(new int[] {5, 7, 7, 8, 8, 10}, 7);
        SearchRotationSort.searchRange(new int[] {5, 7, 7, 8, 8, 10}, 6);

        Word.wordBreak("leetcode", Arrays.asList(new String[]{"leet","code"}));
        Word.wordBreak("applepenapple", Arrays.asList(new String[]{"apple","pen"}));

        MaxRectangle.maximalRectangle(new char[][]{{'0','1','1','0','1'},{'1','1','0','1','0'},{'0','1','1','1','0'},{'1','1','1','1','0'},{'1','1','1','1','1'}});


        StringAlgorithm.maxLengthSubstring("1234367892");
    }
}
