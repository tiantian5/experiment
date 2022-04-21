package com.experiment.core.newlicode;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/01/4:51 下午
 * @Description:
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 */
public class ArrayForTwoSum {

    public static void main(String[] args) {
        int[] arrays = new int[]{2,7,11,15};
        System.out.println(JSONObject.toJSONString(arrayForTwoSum1(arrays, 9)));
        System.out.println(JSONObject.toJSONString(arrayForTwoSum2(arrays, 9)));
    }

    /**
     * 思路
     * 标签：哈希映射
     * 这道题本身如果通过暴力遍历的话也是很容易解决的，时间复杂度在 O(n2)O(n2)
     * 由于哈希查找的时间复杂度为 O(1)O(1)，所以可以利用哈希容器 map 降低时间复杂度
     * 遍历数组 nums，i 为当前下标，每个值都判断map中是否存在 target-nums[i] 的 key 值
     * 如果存在则找到了两个值，如果不存在则将当前的 (nums[i],i) 存入 map 中，继续遍历直到找到为止
     * 如果最终都没有结果则抛出异常
     *
     * 其实就是根据两数之和等于最终的数来做的
     */
    private static int[] arrayForTwoSum2(int[] arrays, int target) {
        if (arrays == null || arrays.length < 1) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arrays.length; i++) {
            int temp = target - arrays[i];
            if (map.containsKey(temp)) {
                return new int[] {map.get(temp),i};
            }
            map.put(arrays[i], i);
        }

        return null;
    }

    private static int[] arrayForTwoSum1(int[] arrays, int target) {

        if (arrays == null || arrays.length < 1) {
            return null;
        }
        for (int i = 0;i < arrays.length; i ++) {
            for (int j = 0; j < arrays.length; j++) {
                if (i != j && target == arrays[i] + arrays[j]) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

}
