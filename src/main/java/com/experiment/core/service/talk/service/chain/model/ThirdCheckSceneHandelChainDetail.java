package com.experiment.core.service.talk.service.chain.model;

import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.service.chain.AbstractTalkCheckHandlerChain;
import org.springframework.stereotype.Component;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 2:31 下午
 **/

@Component
public class ThirdCheckSceneHandelChainDetail extends AbstractTalkCheckHandlerChain {

    @Override
    public Boolean progress(SendMessageDTO sendMessageDTO) {

        autowiredBean();

        return Boolean.TRUE;

    }

    @Override
    public void autowiredBean() {

    }

}