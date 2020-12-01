package com.experiment.core.service.dutychain.chainone.handelchain;

import com.experiment.core.service.dutychain.chainone.context.DetailContext;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 2:21 下午
 **/
public abstract class AbstractDetailHandlerChain {

    private AbstractDetailHandlerChain nextHandler;

    /**
     * 调用下一条链路
     *
     * @param detailContext 上下文
     * @return 是否执行成功
     */
    public final Boolean handleMessage(DetailContext detailContext){
        Boolean currentHandlerResult = progress(detailContext);
        if(currentHandlerResult != null && currentHandlerResult && nextHandler != null){
            currentHandlerResult = nextHandler.handleMessage(detailContext);
        }
        return currentHandlerResult;
    }

    /**
     * 处理器
     *
     * @param detailContext 上下文
     * @return 处理结果
     */
    public abstract Boolean progress(DetailContext detailContext);

    /**
     * 子类中导入需要的bean
     */
    public abstract void autowiredBean();

    /**
     * 获取下一个链路
     *
     * @return 链路
     */
    public AbstractDetailHandlerChain getNextHandler() {
        return nextHandler;
    }

    /**
     * 下一个链路
     *
     * @param nextHandler 下一个链路
     * @return 下一个链路
     */
    public AbstractDetailHandlerChain setNextHandler(AbstractDetailHandlerChain nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

}