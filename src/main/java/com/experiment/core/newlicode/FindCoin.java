package com.experiment.core.newlicode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/02/2:07 下午
 * @Description:
 * 假如老板要找给我15块钱，他有上面的面值分别为10，5，1的硬币数，为了找给我最少的硬币数，
 * 那么他是不是该这样找呢，先看看该找多少个10块的，诶15／10＝1，好像是1个，余下5块，
 * 再找找多少个5块的，5/5 = 1，余下0，结束。
 */
public class FindCoin {

    public static void main(String[] args) {
        //找零钱
        int[] m = {1, 5, 10};
        int target = 15;
        int[] results = giveMoney(m, target);
        System.out.println(target + "的找钱方案:");
        for (int i = 0; i < results.length; i++) {
            System.out.println(results[i] + "枚" + m[i] + "面值");
        }
    }

    public static int[] giveMoney(int[] m, int target) {
        int k = m.length;
        int[] num = new int[k];
        for (int i = 0; i < k; i++) {
            num[i] = target / m[i];
            target = target % m[i];
        }
        return num;
    }

}
