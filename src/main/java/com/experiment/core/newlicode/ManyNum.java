package com.experiment.core.newlicode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/08/4:15 下午
 * @Description: 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于⌊ n/2 ⌋的元素。
 * <p>
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 * <p>
 * 输入：[3,2,3]
 * 输出：3
 */
public class ManyNum {

    public static void main(String[] args) {
        System.out.println(majorityElement(new int[] {3, 2, 3, 2, 2, 2, 4}));
        System.out.println(array(new int[] {3, 2, 3, 2, 2, 2, 4, 2, 2, 4, 4, 4, 4, 4, 4, 4}));
    }

    public static int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(8);
        if (nums == null || nums.length < 1) {
            return -1;
        }
        for (int num : nums) {
            if (map.get(num) == null) {
                map.put(num, 1);
                continue;
            }
            Integer integer = map.get(num);
            map.put(num, integer + 1);
        }

        Integer temp = -1;
        Integer result = -1;
        for (Integer key : map.keySet()) {
            Integer integer = map.get(key);
            if (integer > temp) {
                temp = integer;
                result = key;
            }
        }

        return result;

    }

    /**
     * 因为大于2/n也就是一半 那排序后肯定是在元素中间的一个值
     */
    public static int array(int[] arr) {

        // 冒泡排序
        for (int i = 0;i < arr.length; i ++) {
            for (int j = 0; j < arr.length - 1 -i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        // 现成的排序工具
//        Arrays.sort(arr);
        return arr[arr.length / 2];
    }

}
