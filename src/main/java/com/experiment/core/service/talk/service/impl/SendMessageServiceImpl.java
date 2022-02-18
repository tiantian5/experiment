package com.experiment.core.service.talk.service.impl;

import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.fastjson.JSONObject;
import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.dto.SendMessageResultDTO;
import com.experiment.core.service.talk.enums.EnumSendPlatform;
import com.experiment.core.service.talk.enums.EnumSendType;
import com.experiment.core.service.talk.service.SendMessageService;
import com.experiment.core.service.talk.service.context.SendMessageContext;
import com.experiment.core.service.talk.service.factory.SendMessageFactory;
import com.experiment.core.service.talk.service.factory.SendMessageStrategy;
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
    public void sendMessage(SendMessageDTO sendMessageDTO) {

        // 1、校验信息是否完整
        Assert.notNull(sendMessageDTO, "入参为空");
        Assert.notNull(EnumSendPlatform.getSendPlatformByType(sendMessageDTO.getPlatFormCode()), "入参发送平台为空");
        Assert.notNull(EnumSendType.getEnumSendTypeByType(sendMessageDTO.getMsgType()), "入参发送类型为空");
        Assert.notNull(sendMessageDTO.getJson(), "入参发送内容为空");
        Assert.notNull(sendMessageDTO.getRobotUrl(), "入参机器人链接为空");
        if (EnumSendPlatform.DING_DING_TALK.equals(EnumSendPlatform.getSendPlatformByType(sendMessageDTO.getPlatFormCode()))
                && StringUtils.isBlank(sendMessageDTO.getSign())) {
            throw new RuntimeException("入参发送钉钉平台验签为空");
        }

        // 校验平台是否有此推送类型
        this.checkSendMessageInfo(sendMessageDTO);

        // 2、根据平台分流处理
        SendMessageStrategy dealFactoryTwoStrategy = sendMessageFactory.getDealFactoryTwoStrategy(sendMessageDTO.getPlatFormCode());
        // 将外部信息转换为可发送的实体
        SendMessageContext sendMessageContext = dealFactoryTwoStrategy.assembleSendMsg(sendMessageDTO);
        // 发送消息
        String result = dealFactoryTwoStrategy.sendMessageForPlatForm(sendMessageContext, sendMessageDTO);
        if (StringUtils.isBlank(result) || JSONObject.parseObject(result, SendMessageResultDTO.class) == null
                || JSONObject.parseObject(result, SendMessageResultDTO.class).getErrcode() != 0) {
            throw new RuntimeException("推送消息异常，errorMsg:" + result);
        }

    }

    /**
     * 校验平台是否有此推送类型
     *
     * @param sendMessageDTO 入参
     */
    private void checkSendMessageInfo(SendMessageDTO sendMessageDTO) {

        EnumSendType enumSendTypeByType = EnumSendType.getEnumSendTypeByType(sendMessageDTO.getMsgType());
        if (!enumSendTypeByType.getApplyPlatFormList().contains(sendMessageDTO.getPlatFormCode())) {
            throw new RuntimeException("该推送类型此平台不支持，请确认");
        }

    }

}
