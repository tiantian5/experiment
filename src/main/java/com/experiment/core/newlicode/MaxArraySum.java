package com.experiment.core.newlicode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/11/9:37 上午
 * @Description:
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组 是数组中的一个连续部分。
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 */
public class MaxArraySum {

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[] {-2, 4, -1, 2, -5}));
    }

    public static int maxSubArray(int[] nums) {

        int ans = nums[0];
        int sum = 0;
        for(int num: nums) {
            System.out.println("num:" + num);
            if(sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            System.out.println("sum:" + sum);
            ans = Math.max(ans, sum);
            System.out.println("ans:" + ans);
        }
        return ans;

    }

}
