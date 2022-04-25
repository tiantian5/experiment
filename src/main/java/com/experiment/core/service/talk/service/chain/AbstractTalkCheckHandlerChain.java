package com.experiment.core.service.talk.service.chain;

import com.experiment.core.service.talk.dto.SendMessageDTO;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/25/11:03 上午
 * @Description: 三方平台推送消息校验链路
 */
public abstract class AbstractTalkCheckHandlerChain {

    private AbstractTalkCheckHandlerChain nextHandler;

    /**
     * 调用下一条链路
     *
     * @param sendMessageDTO 上下文
     * @return 是否执行成功
     */
    public final Boolean handleMessage(SendMessageDTO sendMessageDTO){
        Boolean currentHandlerResult = progress(sendMessageDTO);
        if(currentHandlerResult != null && currentHandlerResult && nextHandler != null){
            currentHandlerResult = nextHandler.handleMessage(sendMessageDTO);
        }
        return currentHandlerResult;
    }

    /**
     * 处理器
     *
     * @param sendMessageDTO 上下文
     * @return 处理结果
     */
    public abstract Boolean progress(SendMessageDTO sendMessageDTO);

    /**
     * 子类中导入需要的bean
     */
    public abstract void autowiredBean();

    /**
     * 获取下一个链路
     *
     * @return 链路
     */
    public AbstractTalkCheckHandlerChain getNextHandler() {
        return nextHandler;
    }

    /**
     * 下一个链路
     *
     * @param nextHandler 下一个链路
     * @return 下一个链路
     */
    public AbstractTalkCheckHandlerChain setNextHandler(AbstractTalkCheckHandlerChain nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

}
