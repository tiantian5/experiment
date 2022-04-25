package com.experiment.core.service.talk.service.strategy;

import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.service.context.SendMessageContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/3:01 下午
 * @Description: 业务分发处理
 */
public interface SendMessageStrategy {

    /**
     * 链路信息校验
     *
     * @param sendMessageDTO 入参
     * @return Object
     */
    Object chainCheck(SendMessageDTO sendMessageDTO);

    /**
     * 组装信息
     *
     * @param sendMessageDTO 外界传入
     * @return 内部处理
     */
    SendMessageContext assembleSendMsg(SendMessageDTO sendMessageDTO);

    /**
     * 根据不同平台、类型进行推送消息
     *
     * @param sendMessageContext 上下文
     * @param sendMessageDTO 入参
     * @return String
     */
    String sendMessageForPlatForm(SendMessageContext sendMessageContext, SendMessageDTO sendMessageDTO);

    /**
     * 获取推送平台
     *
     * @return 平台code
     */
    Integer source();

}
