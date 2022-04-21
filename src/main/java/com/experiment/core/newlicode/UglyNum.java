package com.experiment.core.newlicode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/08/5:26 下午
 * @Description:
 */
public class UglyNum {

    public static void main(String[] args) {
        System.out.println(isUgly(14));
    }

    private static boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        // 1,2,3,4,5,6 都是丑数
        if (n <= 6) {
            return true;
        }

        if (n % 2 == 0) {
            return isUgly(n / 2);
        }
        if (n % 3 == 0) {
            return isUgly(n / 3);
        }
        if (n % 5 == 0) {
            return isUgly(n / 5);
        }

        return false;
    }

}
