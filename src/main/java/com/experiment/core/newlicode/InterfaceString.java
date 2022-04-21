package com.experiment.core.newlicode;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/07/9:24 下午
 * @Description:
 */
public class InterfaceString {

    public static void main(String[] args) {
        try {
            String s = new String("abc");
            Field value = s.getClass().getDeclaredField("value");
            value.setAccessible(true);
            value.set(s, "abcd".toCharArray());
            System.out.println(s);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 若s=='abc'，不改变引用指向 最后输出'abcd'
     *
     * @param s s
     * @return String
     */
    private static String getValue(String s) throws NoSuchFieldException, IllegalAccessException {

        if (StringUtils.isBlank(s)) {
            return StringUtils.EMPTY;
        }
        Field value = s.getClass().getDeclaredField("value");
        value.setAccessible(true);
        value.set(s, "abcd".toCharArray());
        return s;

    }

}
