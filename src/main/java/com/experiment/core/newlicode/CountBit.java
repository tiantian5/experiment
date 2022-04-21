package com.experiment.core.newlicode;

import com.alibaba.fastjson.JSONObject;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/19/6:17 下午
 * @Description:
 * 给你一个整数 n ，对于 0 <= i <= n 中的每个 i ，计算其二进制表示中 1 的个数 ，返回一个长度为 n + 1 的数组 ans 作为答案。
 *
 * 输入：n = 2
 * 输出：[0,1,1]
 * 解释：
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 */
public class CountBit {

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(countBits(2)));
        System.out.println(JSONObject.toJSONString(countBits1(2)));
    }

    public static int[] countBits(int n) {

        int[] result = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            if (i == 0) {
                result[0] = 0;
                continue;
            }
            int count = 0;
            int temp = i;
            while (temp != 0) {
                if (temp % 2 == 1) {
                    count ++;
                }
                temp = temp / 2;
            }
            result[i] = count;
        }

        return result;

    }

    public static int[] countBits1(int num) {

        int[] result = new int[num + 1];
        for(int i = 1; i <= num; i++){
            result[i] = result[i >> 1] + (i & 1);
        }
        return result;

    }

}
