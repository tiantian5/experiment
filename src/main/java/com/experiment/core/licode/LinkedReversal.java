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
 *
 * 递归：
 * 5 4 3 2 1
 * 1 2
 * 1 2 3
 * 1 2 3 4
 *
 * 非递归：
 * 5 4 3 2 1
 * 5
 * 4 5
 * 3 4 5
 * 2 3 4 5
 * 1 2 3 4 5
 *
 */
public class LinkedReversal {

    public static LinkedNode reserve(LinkedNode head) {

        if (head == null || head.getNext() == null) {
            return head;
        }

        LinkedNode reserve = reserve(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return reserve;

    }

    public static LinkedNode reverseList(LinkedNode head) {

        LinkedNode pre = null;
        LinkedNode cur = head;
        while (cur != null) {
            LinkedNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }

        return pre;

    }

    public static void main(String[] args) {
        LinkedNode head = new LinkedNode(5);
        LinkedNode one = new LinkedNode(4);
        LinkedNode two = new LinkedNode(3);
        LinkedNode three = new LinkedNode(2);
        LinkedNode four = new LinkedNode(1);
        head.setNext(one);
        one.setNext(two);
        two.setNext(three);
        three.setNext(four);

        StringBuilder sb = new StringBuilder();
        LinkedNode reserve = reverseList(head);
        sb.append(reserve.getValue());
        while (null != reserve.getNext()) {
            sb.append(reserve.getNext().getValue());
            reserve = reserve.getNext();
        }

        System.out.println(sb);

    }

    public static class LinkedNode {

        private Integer value;

        private LinkedNode next;

        public LinkedNode (Integer value, LinkedNode next) {
            this.value = value;
            this.next = next;
        }

        public LinkedNode (Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public LinkedNode getNext() {
            return next;
        }

        public void setNext(LinkedNode next) {
            this.next = next;
        }
    }

}
