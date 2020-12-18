package com.experiment.core.interview.coder;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @author tzw
 * @description 二叉树的深度优先遍历和广度优先遍历
 *
 * 注：杭州百世快递公司必考题
 *
 *  <p>
 *      广度优先遍历：每一层节点依次访问，访问完一层进入下一层，而且每个节点只能访问一次；需要用到队列（Queue）来存储节点对象,队列的特点就是先进先出
 *      深度优先遍历：对每一个可能的分支路径深入到不能再深入为止，而且每个节点只能访问一次；使用到栈（Stack）这种数据结构。stack的特点是先进后出
 *  </p>
 * @create 2020-12-01 11:08 上午
 **/
public class TreeNodeForEach {

    /**
     * 模拟二叉树节点信息
     */
    public static class TreeNode {
        int temp;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode (int temp) {
            this.temp = temp;
        }
    }

    /**
     * 生成tree
     */
    public static class BinaryTree {
        public TreeNode getBinaryTree(int[] arr, int index) {
            TreeNode node;
            if(index < arr.length) {
                int value = arr[index];
                node = new TreeNode(value);
                node.left = getBinaryTree(arr, index * 2 + 1);
                node.right = getBinaryTree(arr, index * 2 + 2);
                return node;
            }
            return null;
        }
    }

    public static class Solution {
        public List<Integer> printFromTopToBottom1(TreeNode treeNode) {
            if (treeNode == null) {
                return Collections.emptyList();
            }
            List<Integer> integerList = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(treeNode);
            while (!queue.isEmpty()) {
                TreeNode tree = queue.poll();
                if (tree.left != null) {
                    queue.offer(tree.left);
                }
                if (tree.right != null) {
                    queue.offer(tree.right);
                }
                integerList.add(tree.temp);
            }

            return integerList;
        }

        public List<Integer> printFromTopToBottom2(TreeNode root) {
            List<Integer> lists= new ArrayList<>();
            if(root==null) {
                return lists;
            }
            Stack<TreeNode> stack= new Stack<>();
            stack.push(root);
            while(!stack.isEmpty()){
                TreeNode tree=stack.pop();
                if(tree.right!=null) {
                    stack.push(tree.right);
                }
                if(tree.left!=null) {
                    stack.push(tree.left);
                }
                lists.add(tree.temp);
            }
            return lists;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        BinaryTree binaryTree = new BinaryTree();
        TreeNode root = binaryTree.getBinaryTree(arr, 0);
        Solution solution = new Solution();
        System.out.println(JSONObject.toJSONString(solution.printFromTopToBottom1(root)));
        System.out.println(JSONObject.toJSONString(solution.printFromTopToBottom2(root)));
    }

}