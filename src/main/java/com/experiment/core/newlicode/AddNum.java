package com.experiment.core.newlicode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/08/4:58 下午
 * @Description: 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。返回这个结果。
 * 输入: num = 38
 * 输出: 2
 * 解释: 各位相加的过程为：
 * 38 --> 3 + 8 --> 11
 * 11 --> 1 + 1 --> 2
 * 由于2 是一位数，所以返回 2
 */
public class AddNum {

    public static void main(String[] args) {

        System.out.println(addDigits(381));
        System.out.println(add2(381));
    }

    public static int addDigits(int num) {
        if (num < 10) {
            return num;
        }
        if (num % 9 == 0) {
            return 9;
        }
        return num % 9;
    }

    public static int add2(int num) {
        while (num >= 10) {
            int sum = 0;
            while (num > 0) {
                sum += num % 10;
                num = num / 10;
            }
            num = sum;
        }

        return num;
    }

}
