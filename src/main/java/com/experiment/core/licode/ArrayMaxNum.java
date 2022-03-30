package com.experiment.core.licode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/03/17/10:12 上午
 * @Description: 例如：1,3,5,7,9,8,6,4,2，请写一个函数找出数组最大的元素
 */
public class ArrayMaxNum {

    public static void main(String[] args) {

        int[] nums = {1,3,5,7,9,8,6,4,2};
        System.out.println(getMaxNum(nums));
    }

    private static int getLargestNumInArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) / 2);
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return nums[left];
    }

    private static int getMaxNum(int[] nums) {
        if (nums == null || nums.length < 1) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid - 1;
            } else {
                right = mid + 1;
            }
        }

        return nums[left];
    }

}
