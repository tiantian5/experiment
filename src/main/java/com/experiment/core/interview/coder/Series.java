package com.experiment.core.interview.coder;

import java.util.stream.Stream;

/**
 * @author tzw
 * @description 快速打印斐波纳契数列
 *
 * 注：杭州深睿医疗面试题
 *
 * <p>
 *     斐波纳契数列示例(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55...数列中开始的两个数字是0和1，后续的每个数字都是前两个数字之和)
 * </p>
 * @create 2020-12-01 11:45 上午
 **/
public class Series {

    public static void main(String[] args) {
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1],t[0] + t[1]})
                .limit(10).map(t -> t[0]).forEach(System.out::println);
    }

}