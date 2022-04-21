package com.experiment.core.newlicode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/11/2:16 下午
 * @Description:
 *
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 输入：n = 3
 * 输出：3
 * 解释：有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 *
 * 其实就是一个公示的推导 f(n - 1) + f(n -2)
 */
public class ClimbStairs {

    public static void main(String[] args) {
        System.out.println(climbStairs(4));
        System.out.println(climeStairs2(3));
    }

    /**
     * 斐波那契数列
     */
    public static int climbStairs(int n) {

        int p = 1, q = 2, r = 0;
        if(n == 1) {
            return p;
        } else if(n == 2) {
            return q;
        }

        for(int i = 3; i <= n; i++){
            r = p + q;
            p = q;
            q = r;
        }
        return r;

    }

    /**
     * 滚动数组记录两个数据
     */
    public static int climeStairs2(int n) {
        if (n == 1) {
            return 1;
        }
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        return second;
    }

}
