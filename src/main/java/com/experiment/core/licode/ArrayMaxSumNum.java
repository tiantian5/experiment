package com.experiment.core.licode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/03/16/3:33 下午
 * @Description: 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组 是数组中的一个连续部分。
 * <p>
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 */
public class ArrayMaxSumNum {

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxNum(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    public static int maxSubArray(int[] nums) {

        if (nums == null || nums.length < 1) {
            return 0;
        }
        int res = nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = dp[i - 1];
            int numTemp = nums[i];
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public static int maxNum(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum > max) {
                    max = sum;
                }
            }
        }

        return max;
    }

}
