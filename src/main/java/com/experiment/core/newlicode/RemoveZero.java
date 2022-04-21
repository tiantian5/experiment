package com.experiment.core.newlicode;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/08/4:41 下午
 * @Description: 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 */
public class RemoveZero {

    public static void main(String[] args) {
        int[] nums = new int[] {0, 0, 0, 3, 12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeroes(int[] nums) {
        if (nums == null || nums.length < 1) {
            return;
        }
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i > j) {
                    nums[j] = nums[i];
                    nums[i] = 0;
                }
                j ++;
            }
        }
    }

}
