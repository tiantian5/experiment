package com.experiment.core.service.factory.factorytwo;

/**
 * @author tzw
 * @description 业务分发
 * @create 2020-11-30 11:12 上午
 **/
public interface DealFactoryTwoStrategy {

    /**
     * 组装业务前期处理上下文
     *
     * @param obj 组装所需参数
     */
    void buildDealContext(Object ... obj);

    /**
     * 业务处理
     *
     * @param obj 处理所需参数
     */
    void dealHandel(Object obj);

    /**
     * 业务来源
     *
     * @return 业务方code
     */
    Integer source();

}