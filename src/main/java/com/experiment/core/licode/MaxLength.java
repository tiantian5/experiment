package com.experiment.core.licode;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2021/06/19/3:09 下午
 * @Description:
 * <p>
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
 * </p>
 */
public class MaxLength {

    public static void main(String[] args) {
        int[] nums = {3,2,4};
        MaxLength maxLength = new MaxLength();
        System.out.println(Arrays.toString(maxLength.twoSum(nums, 9)));
    }

    public int[] twoSum(int[] nums, int target) {
        if (nums == null) {
            return null;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] + nums[j] == target && nums[i] != nums[j]) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

}
