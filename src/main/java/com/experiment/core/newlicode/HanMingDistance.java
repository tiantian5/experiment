package com.experiment.core.newlicode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/19/6:08 下午
 * @Description:
 * 两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
 * 给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
 *
 * 输入：x = 1, y = 4
 * 输出：2
 * 解释：
 * 1   (0 0 0 1)
 * 4   (0 1 0 0)
 *        ↑   ↑
 * 上面的箭头指出了对应二进制位不同的位置。
 */
public class HanMingDistance {

    public static void main(String[] args) {
        System.out.println(hammingDistance(-3, -4));
        System.out.println(hammingDistance2(-3, -4));
    }

    public static int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    public static int hammingDistance2(int x, int y) {

        // 模2取余 余数不等的即距离加一 (也就是二进制对应位不一样)
        int distance = 0;
        // 都为0的时候再结束
        while (x != 0 || y != 0) {
            if (x % 2 != y % 2) {
                distance ++;
            }
            x /= 2;
            y /= 2;
        }

        return distance;

    }

}
