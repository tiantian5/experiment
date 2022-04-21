package com.experiment.core.service.talk.service.factory.model;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.experiment.core.service.talk.bo.SendMessageImageBO;
import com.experiment.core.service.talk.bo.SendMessageLinkBO;
import com.experiment.core.service.talk.bo.SendMessageTextBO;
import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.enums.EnumSendPlatform;
import com.experiment.core.service.talk.enums.EnumSendType;
import com.experiment.core.service.talk.params.DingDingImageDTO;
import com.experiment.core.service.talk.params.DingDingLinkDTO;
import com.experiment.core.service.talk.params.DingDingTextDTO;
import com.experiment.core.service.talk.service.context.SendMessageContext;
import com.experiment.core.service.talk.service.factory.SendMessageStrategy;
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
public class DingDingSendMessageModel implements SendMessageStrategy {

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

        SendMessageTextBO sendMessageTextBO = (SendMessageTextBO) sendMessageDTO.getObj();
        if (sendMessageTextBO == null || StringUtils.isBlank(sendMessageTextBO.getContext())) {
            throw new RuntimeException("解析文本信息异常，请检查数据是否正确传入");
        }

        DingDingTextDTO dingDingTextDTO = new DingDingTextDTO();
        dingDingTextDTO.setMsgtype(EnumSendType.TEXT.getSendNameEn());

        DingDingTextDTO.DingDingAt dingDingAt = new DingDingTextDTO.DingDingAt();
        if (sendMessageTextBO.getIsAll() != null
                || CollectionUtils.isNotEmpty(sendMessageTextBO.getPhoneList())
                || CollectionUtils.isNotEmpty(sendMessageTextBO.getUserIdList())) {
            dingDingAt.setAtMobiles(sendMessageTextBO.getPhoneList());
            dingDingAt.setAtUserIds(sendMessageTextBO.getUserIdList());
            dingDingAt.setIsAtAll(sendMessageTextBO.getIsAll());
            dingDingTextDTO.setAt(dingDingAt);
        }

        DingDingTextDTO.DingDingText dingDingText = new DingDingTextDTO.DingDingText();
        dingDingText.setContent(sendMessageTextBO.getContext());
        dingDingTextDTO.setText(dingDingText);

        sendMessageContext.setDingDingTextDTO(dingDingTextDTO);

    }

    /**
     * 链接组装
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealLink(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        SendMessageLinkBO sendMessageLinkBO = (SendMessageLinkBO)sendMessageDTO.getObj();
        if (sendMessageLinkBO == null
                || StringUtils.isBlank(sendMessageLinkBO.getText())
                || StringUtils.isBlank(sendMessageLinkBO.getTitle())
                || StringUtils.isBlank(sendMessageLinkBO.getPicUrl())
                || StringUtils.isBlank(sendMessageLinkBO.getMessageUrl())) {
            throw new RuntimeException("解析链接信息异常，请检查数据是否正确传入");
        }

        DingDingLinkDTO dingDingLinkDTO = new DingDingLinkDTO();
        dingDingLinkDTO.setMsgtype(EnumSendType.LINK.getSendNameEn());

        DingDingLinkDTO.DingDingLink dingDingLink = new DingDingLinkDTO.DingDingLink();
        dingDingLink.setMessageUrl(sendMessageLinkBO.getMessageUrl());
        dingDingLink.setPicUrl(sendMessageLinkBO.getPicUrl());
        dingDingLink.setTitle(sendMessageLinkBO.getTitle());
        dingDingLink.setText(sendMessageLinkBO.getText());
        dingDingLinkDTO.setLink(dingDingLink);

        sendMessageContext.setDingDingLinkDTO(dingDingLinkDTO);
    }

    /**
     * 图片组装
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealImage(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        SendMessageImageBO sendMessageImageBO = (SendMessageImageBO) sendMessageDTO.getObj();
        if (sendMessageImageBO == null
                || StringUtils.isBlank(sendMessageImageBO.getPicUrl())) {
            throw new RuntimeException("解析图片信息异常，请检查数据是否正确传入");
        }

        DingDingImageDTO dingDingImageDTO = new DingDingImageDTO();
        dingDingImageDTO.setMsgtype(EnumSendType.IMAGE.getSendNameEn());

        DingDingImageDTO.DingDingImage dingDingImage = new DingDingImageDTO.DingDingImage();
        dingDingImage.setPicUrl(sendMessageImageBO.getPicUrl());
        dingDingImageDTO.setImage(dingDingImage);

        sendMessageContext.setDingDingImageDTO(dingDingImageDTO);

    }

}
