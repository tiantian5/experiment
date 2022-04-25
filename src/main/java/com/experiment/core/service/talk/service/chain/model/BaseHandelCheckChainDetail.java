package com.experiment.core.service.talk.service.chain.model;

import com.alibaba.dubbo.common.utils.Assert;
import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.enums.EnumSendPlatform;
import com.experiment.core.service.talk.enums.EnumSendType;
import com.experiment.core.service.talk.service.chain.AbstractTalkCheckHandlerChain;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author tzw
 * @description
 * @create 2020-11-30 2:21 下午
 *
 * 基础校验链路
 **/

@Component
public class BaseHandelCheckChainDetail extends AbstractTalkCheckHandlerChain {

    @Override
    public Boolean progress(SendMessageDTO sendMessageDTO) {

        autowiredBean();

        // 1、校验信息是否完整
        Assert.notNull(sendMessageDTO, "入参为空");
        Assert.notNull(EnumSendPlatform.getSendPlatformByType(sendMessageDTO.getPlatFormCode()), "入参发送平台为空");
        Assert.notNull(EnumSendType.getEnumSendTypeByType(sendMessageDTO.getMsgType()), "入参发送类型为空");
        Assert.notNull(sendMessageDTO.getObj(), "入参发送内容为空");
        Assert.notNull(sendMessageDTO.getRobotUrl(), "入参机器人链接为空");
        if (EnumSendPlatform.DING_DING_TALK.equals(EnumSendPlatform.getSendPlatformByType(sendMessageDTO.getPlatFormCode()))
                && StringUtils.isBlank(sendMessageDTO.getSign())) {
            throw new RuntimeException("入参发送钉钉平台验签为空");
        }

        // 校验平台是否有此推送类型
        this.checkSendMessageInfo(sendMessageDTO);

        return Boolean.TRUE;
    }

    @Override
    public void autowiredBean() {

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