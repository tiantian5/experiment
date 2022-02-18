package com.experiment.core.service.talk.service.factory.model;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.experiment.core.service.talk.bo.SendMessageImageBO;
import com.experiment.core.service.talk.bo.SendMessageNewsBO;
import com.experiment.core.service.talk.bo.SendMessageTextBO;
import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.enums.EnumSendPlatform;
import com.experiment.core.service.talk.enums.EnumSendType;
import com.experiment.core.service.talk.params.WeChatImageDTO;
import com.experiment.core.service.talk.params.WeChatNewsDTO;
import com.experiment.core.service.talk.params.WeChatTextDTO;
import com.experiment.core.service.talk.service.context.SendMessageContext;
import com.experiment.core.service.talk.service.factory.SendMessageStrategy;
import com.experiment.core.service.talk.util.SendMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/3:13 下午
 * @Description:
 */

@Service
public class WeChatSendMessageModel implements SendMessageStrategy {

    @Override
    public SendMessageContext assembleSendMsg(SendMessageDTO sendMessageDTO) {

        SendMessageContext sendMessageContext = new SendMessageContext();

        EnumSendType enumSendTypeByType = EnumSendType.getEnumSendTypeByType(sendMessageDTO.getMsgType());
        switch (enumSendTypeByType) {
            case TEXT:
                this.dealText(sendMessageDTO, sendMessageContext);
                break;

            case IMAGE_TEXT:
                this.dealNews(sendMessageDTO, sendMessageContext);
                break;

            case IMAGE:
                this.dealImage(sendMessageDTO, sendMessageContext);
                break;

            case FILE:
                this.dealFile(sendMessageDTO, sendMessageContext);
                break;
vcdsaq  aszxc dsxc
            default:
                break;

        }
        return sendMessageContext;

    }

    /**
     * 组装图文
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealNews(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        List<SendMessageNewsBO> sendMessageNewsBOList = JSONObject.parseArray(sendMessageDTO.getJson(), SendMessageNewsBO.class);
        if (CollectionUtils.isEmpty(sendMessageNewsBOList)) {
            throw new RuntimeException("解析图文信息异常，请检查数据是否正确传入");
        }
        for (SendMessageNewsBO newsBO : sendMessageNewsBOList) {
            if (newsBO == null || StringUtils.isBlank(newsBO.getTitle())
                    || StringUtils.isBlank(newsBO.getDescription()) || StringUtils.isBlank(newsBO.getUrl())
                    || StringUtils.isBlank(newsBO.getPicUrl())) {
                throw new RuntimeException("解析图文信息异常，请检查数据是否正确传入");
            }
        }

        WeChatNewsDTO weChatNewsDTO = new WeChatNewsDTO();
        weChatNewsDTO.setMsgtype(EnumSendType.IMAGE_TEXT.getSendNameEn());

        WeChatNewsDTO.WeChatNews weChatNews = new WeChatNewsDTO.WeChatNews();
        List<WeChatNewsDTO.Article> articles = new ArrayList<>();
        for (SendMessageNewsBO newsBO : sendMessageNewsBOList) {
            WeChatNewsDTO.Article article = new WeChatNewsDTO.Article();
            article.setTitle(newsBO.getTitle());
            article.setDescription(newsBO.getDescription());
            article.setPicurl(newsBO.getUrl());
            article.setUrl(newsBO.getUrl());
            articles.add(article);
        }
        weChatNews.setArticles(articles);

        weChatNewsDTO.setNews(weChatNews);

        sendMessageContext.setWeChatNewsDTO(weChatNewsDTO);

    }

    /**
     * 组装图片
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealImage(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        SendMessageImageBO sendMessageImageBO = JSONObject.parseObject(sendMessageDTO.getJson(), SendMessageImageBO.class);
        if (sendMessageImageBO == null
                || StringUtils.isBlank(sendMessageImageBO.getPicUrl())) {
            throw new RuntimeException("解析图片信息异常，请检查数据是否正确传入");
        }

        WeChatImageDTO weChatImageDTO = new WeChatImageDTO();
        weChatImageDTO.setMsgtype(EnumSendType.IMAGE.getSendNameEn());

        WeChatImageDTO.WeChatImage weChatImage = new WeChatImageDTO.WeChatImage();
        weChatImage.setBase64(SendMessageUtil.imageToBase64(sendMessageImageBO.getPicUrl()));
        weChatImage.setMd5(SendMessageUtil.md5(sendMessageImageBO.getPicUrl()));
        weChatImageDTO.setImage(weChatImage);

        sendMessageContext.setWeChatImageDTO(weChatImageDTO);

    }

    /**
     * 组装文本
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealText(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        SendMessageTextBO sendMessageTextBO = JSONObject.parseObject(sendMessageDTO.getJson(), SendMessageTextBO.class);
        if (sendMessageTextBO == null || StringUtils.isBlank(sendMessageTextBO.getContext())) {
            throw new RuntimeException("解析文本信息异常，请检查数据是否正确传入");
        }

        WeChatTextDTO weChatTextDTO = new WeChatTextDTO();
        weChatTextDTO.setMsgtype(EnumSendType.TEXT.getSendNameEn());

        WeChatTextDTO.WeChatText weChatText = new WeChatTextDTO.WeChatText();
        if (sendMessageTextBO.getIsAll() != null
                || CollectionUtils.isNotEmpty(sendMessageTextBO.getPhoneList())
                || CollectionUtils.isNotEmpty(sendMessageTextBO.getUserIdList())) {
            weChatText.setContent(sendMessageTextBO.getContext());
            weChatText.setMentioned_list(sendMessageTextBO.getUserIdList());
            List<String> phoneList = sendMessageTextBO.getPhoneList();
            if (sendMessageTextBO.getIsAll()) {
                if (CollectionUtils.isEmpty(phoneList)) {
                    phoneList = new ArrayList<>();
                }
                phoneList.add("@all");
            }
            weChatText.setMentioned_mobile_list(phoneList);
        }
        weChatTextDTO.setText(weChatText);

        sendMessageContext.setWeChatTextDTO(weChatTextDTO);

    }

    @Override
    public String sendMessageForPlatForm(SendMessageContext sendMessageContext, SendMessageDTO sendMessageDTO) {

        return null;
    }

    @Override
    public Integer source() {
        return EnumSendPlatform.ENTERPRISE_WECHAT.getType();
    }

}
