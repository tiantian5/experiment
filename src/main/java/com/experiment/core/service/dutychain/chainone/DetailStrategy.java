package com.experiment.core.service.dutychain.chainone;

/**
 * @author tzw
 * @description 策略类
 * @create 2020-11-30 2:12 下午
 **/

public interface DetailStrategy {

    /**
     * 策略+工厂+责任链模式
     *
     * @param obj 请求入参
     * @return 执行结果 组装至上下文中
     */
    Object getDetail(Object ... obj);

    /**
     * 策略类型
     *
     * @return 获取处理业务
     */
    Integer getType();

}