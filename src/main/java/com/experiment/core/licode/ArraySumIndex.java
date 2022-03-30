package com.experiment.core.licode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/03/16/2:59 下午
 * @Description: 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * <p>
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
 */
public class ArraySumIndex {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[] {2, 7, 11, 15}, 9)));
        System.out.println(Arrays.toString(twoSum2(new int[] {11, 15, 7, 2}, 9)));
    }

    public static int[] twoSum(int[] nums, int target) {

        if (nums == null || nums.length == 0) {
            return new int[] {};
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (target == nums[i] + nums[j] && i != j) {
                    return new int[] {i, j};
                }
            }
        }

        return new int[] {};

    }

    /**
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
     */
    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(target - nums[i])) {
                return new int[]{hashMap.get(target - nums[i]), i};
            }
            hashMap.put(nums[i], i);
        }
        return new int[0];
    }


}
