package com.experiment.core.service.talk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.dto.SendMessageResultDTO;
import com.experiment.core.service.talk.service.SendMessageService;
import com.experiment.core.service.talk.service.context.SendMessageContext;
import com.experiment.core.service.talk.service.strategy.SendMessageFactory;
import com.experiment.core.service.talk.service.strategy.SendMessageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/2:37 下午
 * @Description:
 */

@Service
@Slf4j
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    private SendMessageFactory sendMessageFactory;

    @Override
    public String sendMessage(SendMessageDTO sendMessageDTO) {

        // 1、根据平台分流处理
        SendMessageStrategy dealFactoryTwoStrategy = sendMessageFactory.getDealFactoryTwoStrategy(sendMessageDTO.getPlatFormCode());
        // 2、校验+风控校验
        dealFactoryTwoStrategy.chainCheck(sendMessageDTO);
        // 3、将外部信息转换为可发送的实体
        SendMessageContext sendMessageContext = dealFactoryTwoStrategy.assembleSendMsg(sendMessageDTO);
        // 4、发送消息
        String result = dealFactoryTwoStrategy.sendMessageForPlatForm(sendMessageContext, sendMessageDTO);
        if (StringUtils.isBlank(result) || JSONObject.parseObject(result, SendMessageResultDTO.class) == null
                || JSONObject.parseObject(result, SendMessageResultDTO.class).getErrcode() != 0) {
            log.error("推送消息异常，errorMsg:" + result);
        }

        return result;

    }

}
