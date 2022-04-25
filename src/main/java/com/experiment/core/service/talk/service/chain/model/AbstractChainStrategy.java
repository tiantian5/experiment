package com.experiment.core.service.talk.service.chain.model;

import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.service.strategy.SendMessageStrategy;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/25/11:11 上午
 * @Description:
 */
public abstract class AbstractChainStrategy implements SendMessageStrategy {

    /**
     * 父类基础链路
     *
     * @param sendMessageDTO 请求入参
     */
    @Override
    public Object chainCheck(SendMessageDTO sendMessageDTO) {

        BaseHandelCheckChainDetail baseHandelChain = new BaseHandelCheckChainDetail();

        baseHandelChain.setNextHandler(new ThirdCheckSceneHandelChainDetail());

        return baseHandelChain.handleMessage(sendMessageDTO);

    }

}
