package com.example.zxg.myprogram.algorithm;

/**
 * description ：leetcode 21——合并两个有序链表
 * Created by zhuxiaoguang at 10:38 on 2019/9/6
 */
public class MergeTwoLinkList {

    /*public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode end = head;

        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                end.next = l2;
                l2 = l2.next;
            } else {
                end.next = l1;
                l1 = l1.next;
            }
            end = end.next;
        }

        if (l1 != null) {
            end.next = l1;
        } else {
            end.next = l2;
        }

        return head.next;
    }*/

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val > l2.val) {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        } else {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }
    }

    public static class ListNode {
        public int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
