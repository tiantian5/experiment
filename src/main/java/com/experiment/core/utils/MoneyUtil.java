package com.experiment.core.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tzw
 * @description 分转元
 * @create 2020-09-10 3:13 下午
 **/
@Slf4j
public class MoneyUtil {

    /**
     * 分转元
     * Long型金额（分）转String型金额（元）
     *
     * @param fen 分
     * @return 元
     */
    public static String fen2Yuan(Long fen) {
        if (fen == null) {
            return "0";
        }

        if (fen % 100 == 0) {
            return String.valueOf(fen / 100);
        } else {
            return String.valueOf(fen / 100.0);
        }
    }

    /**
     * 分转元，保留两位小数，整数补零
     * @param fen
     * @return
     */
    public static String fen2YuanKeepZero(Long fen){
        return fen / 100 + "." + fen % 100 / 10 + fen % 100 % 10;
    }

    /**
     * 字符串的元 转化为分
     * <p>
     *     主要是字符串可能包含小数点
     * </p>
     * @param str 元
     * @return 分
     */
    public static Long stringYuan2Fen(String str) {
        try {
            StringBuilder c = new StringBuilder();
            if (str.contains(".")) {
                String[] split = str.split("\\.");
                c.append(split[0]);
                if (split[1].length() > 2) {
                    c.append(split[1], 0, 2);
                } else if (split[1].length() == 2) {
                    c.append(split[1]);
                } else if (split[1].length() == 1) {
                    c.append(split[1]).append("0");
                }
                return Long.parseLong(c.toString());
            } else {
                return Long.parseLong(str) * 100;
            }
        } catch (Exception e) {
            log.error("字符串的元 转化为分异常", e);
        }
        return null;
    }
}
