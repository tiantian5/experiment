package com.experiment.core.service.factory.factorythree;

import java.util.List;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 11:29 上午
 **/
public interface ThreeFactoryDrainage {

    /**
     * 具体处理器
     *
     * @param obj 上下文
     */
    void dealWorkOrder(Object obj);

    /**
     * 适配类型
     *
     * @return 适配类型集合
     */
    List<Integer> selfAdaption();

}