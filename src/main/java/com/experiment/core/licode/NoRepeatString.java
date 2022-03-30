package com.experiment.core.licode;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/03/16/2:45 下午
 * @Description: 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是"wke"，所以其长度为 3。
 */
public class NoRepeatString {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }

    public static int lengthOfLongestSubstring(String s) {

        int max = 0;
        int j;
        int flag = 0;
        if ("".equals(s)) {
            return 0;
        }
        for (int i = 1; i < s.length(); i++) {
            int num = 0;
            for (j = flag; j < i; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    flag = j + 1;
                    break;
                } else {
                    num++;
                }
            }
            if (num > max) {
                max = num;
            }
        }
        return max + 1;
    }

}
