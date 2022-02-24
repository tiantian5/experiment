package com.experiment.core.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/02/24/5:08 下午
 * @Description:
 */
public class StringUtils {

    public static void main(String[] args) {
        System.out.printf("已回温%s，%s后可用%n", System.currentTimeMillis(), System.currentTimeMillis() + 60000);
        System.out.println(String.format("已回温%s，%s后可用", System.currentTimeMillis(), System.currentTimeMillis() + 60000));
    }

}
