package com.experiment.core.service.factory.factoryfive.instance;


/**
 * @author tzw
 * @description
 * @create 2020-12-16 11:28 上午
 **/
public abstract class BaseNotifyAction<T> {
    /**
     * 具体触发器
     * @param t t
     */
    protected abstract void realAction(T t);
}
