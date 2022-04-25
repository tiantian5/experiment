package com.experiment.core.service.talk.service.strategy.model;

import com.alibaba.fastjson.JSONObject;
import com.experiment.core.service.talk.dto.SendMessageDTO;
import com.experiment.core.service.talk.enums.EnumSendPlatform;
import com.experiment.core.service.talk.enums.EnumSendType;
import com.experiment.core.service.talk.params.WeChatImageDTO;
import com.experiment.core.service.talk.params.WeChatTextDTO;
import com.experiment.core.service.talk.service.chain.model.AbstractChainStrategy;
import com.experiment.core.service.talk.service.context.SendMessageContext;
import com.experiment.core.service.talk.service.strategy.SendMessageStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/3:13 下午
 * @Description:
 */

@Service
public class WeChatSendMessageModel extends AbstractChainStrategy implements SendMessageStrategy {

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

            case IMAGE_TEXT:
//                this.dealNews(sendMessageDTO, sendMessageContext);
                break;

            case IMAGE:
                this.dealImage(sendMessageDTO, sendMessageContext);
                break;

            case FILE:
//                this.dealFile(sendMessageDTO, sendMessageContext);
                break;
            default:
                break;

        }
        return sendMessageContext;

    }

    /**
     * 组装图片
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealImage(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        WeChatImageDTO weChatImageDTO = JSONObject.parseObject(JSONObject.toJSONString(sendMessageDTO.getObj()), WeChatImageDTO.class);
        if (weChatImageDTO == null
                || weChatImageDTO.getImage() == null
                || StringUtils.isBlank(weChatImageDTO.getImage().getMd5())
                || StringUtils.isBlank(weChatImageDTO.getImage().getBase64())) {
            throw new RuntimeException("解析图片信息异常，请检查数据是否正确传入");
        }

        sendMessageContext.setWeChatImageDTO(weChatImageDTO);

    }

    /**
     * 组装文本
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
    private void dealText(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {

        WeChatTextDTO weChatTextDTO = JSONObject.parseObject(JSONObject.toJSONString(sendMessageDTO.getObj()), WeChatTextDTO.class);
        if (weChatTextDTO == null || weChatTextDTO.getText() == null || StringUtils.isBlank(weChatTextDTO.getText().getContent())) {
            throw new RuntimeException("解析文本信息异常，请检查数据是否正确传入");
        }

        sendMessageContext.setWeChatTextDTO(weChatTextDTO);

    }

    /**
     * 组装图文
     *
     * @param sendMessageDTO 入参
     * @param sendMessageContext 上下文
     */
//    private void dealNews(SendMessageDTO sendMessageDTO, SendMessageContext sendMessageContext) {
//
//        List<SendMessageNewsBO> sendMessageNewsBOList = JSONObject.parseArray(JSONObject.toJSONString(sendMessageDTO.getObj()), SendMessageNewsBO.class);
//        if (CollectionUtils.isEmpty(sendMessageNewsBOList)) {
//            throw new RuntimeException("解析图文信息异常，请检查数据是否正确传入");
//        }
//        for (SendMessageNewsBO newsBO : sendMessageNewsBOList) {
//            if (newsBO == null || StringUtils.isBlank(newsBO.getTitle())
//                    || StringUtils.isBlank(newsBO.getDescription()) || StringUtils.isBlank(newsBO.getUrl())
//                    || StringUtils.isBlank(newsBO.getPicUrl())) {
//                throw new RuntimeException("解析图文信息异常，请检查数据是否正确传入");
//            }
//        }
//
//        WeChatNewsDTO weChatNewsDTO = new WeChatNewsDTO();
//        weChatNewsDTO.setMsgtype(EnumSendType.IMAGE_TEXT.getSendNameEn());
//
//        WeChatNewsDTO.WeChatNews weChatNews = new WeChatNewsDTO.WeChatNews();
//        List<WeChatNewsDTO.Article> articles = new ArrayList<>();
//        for (SendMessageNewsBO newsBO : sendMessageNewsBOList) {
//            WeChatNewsDTO.Article article = new WeChatNewsDTO.Article();
//            article.setTitle(newsBO.getTitle());
//            article.setDescription(newsBO.getDescription());
//            article.setPicurl(newsBO.getUrl());
//            article.setUrl(newsBO.getUrl());
//            articles.add(article);
//        }
//        weChatNews.setArticles(articles);
//
//        weChatNewsDTO.setNews(weChatNews);
//
//        sendMessageContext.setWeChatNewsDTO(weChatNewsDTO);
//
//    }

    @Override
    public String sendMessageForPlatForm(SendMessageContext sendMessageContext, SendMessageDTO sendMessageDTO) {

        return null;
    }

    @Override
    public Integer source() {
        return EnumSendPlatform.ENTERPRISE_WECHAT.getType();
    }

}
