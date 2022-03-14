package com.experiment.core.licode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/03/11/3:33 下午
 * @Description: 链表反转
 * 5 4 3 2 1
 * 1 2 3 4 5
 *
 * 1 普通
 * 2 链表可以选用迭代或递归方式完成反转
 */
public class LinkedReversal {

    public static void main(String[] args) {

        ListNode oneHead = new ListNode(1);
        ListNode twoHead = new ListNode(2);
        ListNode threeHead = new ListNode(3);
        ListNode fourHead = new ListNode(4);
        ListNode fiveHead = new ListNode(5);
        oneHead.setNext(twoHead);
        twoHead.setNext(threeHead);
        threeHead.setNext(fourHead);
        fourHead.setNext(fiveHead);

        ListNode curNode = oneHead;
        while (curNode != null) {
            ListNode listNode = reverseList(curNode);
            int val = listNode.getVal();
            curNode = curNode.getNext();
            System.out.print(val + " ");
        }


    }

    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }

}
