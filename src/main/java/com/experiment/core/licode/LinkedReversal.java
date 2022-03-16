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

        ListNode oneHead = new ListNode(5);
        ListNode twoHead = new ListNode(4);
        ListNode threeHead = new ListNode(3);
        ListNode fourHead = new ListNode(2);
        ListNode fiveHead = new ListNode(1);
        oneHead.setNext(twoHead);
        twoHead.setNext(threeHead);
        threeHead.setNext(fourHead);
        fourHead.setNext(fiveHead);

        StringBuilder sb = new StringBuilder();
        ListNode listNode = reverseList(oneHead);
        sb.append(listNode.getVal());
        while (null != listNode.getNext()) {
            sb.append(" -> ");
            sb.append(listNode.getNext().getVal());
            listNode = listNode.getNext();
        }
        System.out.println(sb);


    }

    public static ListNode reverseList(ListNode head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        ListNode listNode = reverseList(head.next);

        head.getNext().setNext(head);
        head.setNext(null);
        return listNode;
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
