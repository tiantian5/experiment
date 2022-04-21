package com.experiment.core.service.map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/18/9:37 上午
 * @Description:
 *
 * 校验流转状态是否满足预定值
 */
public class StatusCheckMap {

    /**
     * Multimap 可以存储相同值的key
     */
    static Multimap<Integer, Integer> statusMap = ArrayListMultimap.create();
    static {
        statusMap.put(1, 1);
    }

    boolean exist = statusMap.containsEntry(1, 1);

}
