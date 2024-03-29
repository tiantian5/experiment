package com.experiment.core.service.talk.service.strategy.model;

import com.alibaba.fastjson.JSONObject;
import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.enums.EnumSendPlatform;
import com.experiment.core.service.talk.enums.EnumSendType;
import com.experiment.core.service.talk.params.DingDingImageDTO;
import com.experiment.core.service.talk.params.DingDingLinkDTO;
import com.experiment.core.service.talk.params.DingDingTextDTO;
import com.experiment.core.service.talk.service.chain.model.AbstractChainStrategy;
import com.experiment.core.service.talk.service.context.SendMessageContext;
import com.experiment.core.service.talk.service.strategy.SendMessageStrategy;
import com.experiment.core.service.talk.util.SendMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/3:12 下午
 * @Description:
 */

@Service
@Slf4j
public class DingDingSendMessageModel extends AbstractChainStrategy implements SendMessageStrategy {

    @Override
    public Object chainCheck(SendMessageDTO sendMessageDTO) {

        return super.chainCheck(sendMessageDTO);

    }

    @Override
    public SendMessageContext assembleSendMsg(SendMessageDTO sendMessageDTO) {

        SendMessageContext sendMessageContext = new SendMessageContext();

        EnumSendType enumSendTypeByType = EnumSendType.getEnumSendTypeByType(sendMessageDTO.getMsgType());
        switch (enumSendTypeByType) {
            case TEXT:
                this.dealText(sendMessageDTO, sendMessageContext);
                break;

            case LINK:
                this.dealLink(sendMessageDTO, sendMessageContext);
                break;

            case IMAGE:
                this.dealImage(sendMessageDTO, sendMessageContext);
                break;

            default:
                break;

        }
        return sendMessageContext;
    }

    @Override
    public String sendMessageForPlatForm(SendMessageContext sendMessageContext, SendMessageDTO sendMessageDTO) {

        EnumSendType enumSendTypeByType = EnumSendType.getEnumSendTypeByType(sendMessageDTO.getMsgType());
        Map<String, String> headParam = new HashMap<>(8);
        headParam.put("Content-type", "application/json;charset=UTF-8");
        long time = System.currentTimeMillis();
        String result = StringUtils.EMPTY;
        switch (enumSendTypeByType) {
            case TEXT:
                result = SendMessageUtil.httpPostMethod(sendMessageDTO.getRobotUrl() + "&timestamp=" + time + "&sign=" + SendMessageUtil.sign(time, sendMessageDTO.getSign())
                        , headParam, JSONObject.toJSONString(sendMessageContext.getDingDingTextDTO()));
                break;

            case LINK:
                result = SendMessageUtil.httpPostMethod(sendMessageDTO.getRobotUrl() + "&timestamp=" + time + "&sign=" + SendMessageUtil.sign(time, sendMessageDTO.getSign())
                        , headParam, JSONObject.toJSONString(sendMessageContext.getDingDingLinkDTO()));
                break;

            case IMAGE:
                result = SendMessageUtil.httpPostMethod(sendMessageDTO.getRobotUrl() + "&timestamp=" + time + "&sign=" + SendMessageUtil.sign(time, sendMessageDTO.getSign())
                        , headParam, JSONObject.toJSONString(sendMessageContext.getDingDingImageDTO()));
                break;

            default:
                break;

        }

        return result;

    }

    @Override
    public Integer source() {
        return EnumSendPlatform.DING_DING_TALK.getType();
    }

    /**
     * 文本组装
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealText(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        DingDingTextDTO dingDingTextDTO = JSONObject.parseObject(JSONObject.toJSONString(sendMessageDTO.getObj()), DingDingTextDTO.class);
        if (dingDingTextDTO == null || dingDingTextDTO.getText() == null || StringUtils.isBlank(dingDingTextDTO.getText().getContent())) {
            throw new RuntimeException("解析文本信息异常，请检查数据是否正确传入");
        }

        sendMessageContext.setDingDingTextDTO(dingDingTextDTO);

    }

    /**
     * 链接组装
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealLink(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        DingDingLinkDTO dingDingLinkDTO = JSONObject.parseObject(JSONObject.toJSONString(sendMessageDTO.getObj()), DingDingLinkDTO.class);
        if (dingDingLinkDTO == null
                || dingDingLinkDTO.getLink() == null
                || StringUtils.isBlank(dingDingLinkDTO.getMsgtype())
                || StringUtils.isBlank(dingDingLinkDTO.getLink().getTitle())
                || StringUtils.isBlank(dingDingLinkDTO.getLink().getPicUrl())
                || StringUtils.isBlank(dingDingLinkDTO.getLink().getText())
                || StringUtils.isBlank(dingDingLinkDTO.getLink().getMessageUrl())) {
            throw new RuntimeException("解析链接信息异常，请检查数据是否正确传入");
        }

        sendMessageContext.setDingDingLinkDTO(dingDingLinkDTO);
    }

    /**
     * 图片组装
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealImage(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        DingDingImageDTO dingDingImageDTO = JSONObject.parseObject(JSONObject.toJSONString(sendMessageDTO.getObj()), DingDingImageDTO.class);
        if (dingDingImageDTO == null
                || dingDingImageDTO.getImage() == null
                || StringUtils.isBlank(dingDingImageDTO.getImage().getPicUrl())) {
            throw new RuntimeException("解析图片信息异常，请检查数据是否正确传入");
        }

        sendMessageContext.setDingDingImageDTO(dingDingImageDTO);

    }

}
