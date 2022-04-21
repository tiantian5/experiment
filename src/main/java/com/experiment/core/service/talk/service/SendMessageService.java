package com.experiment.core.service.talk.service;

import com.experiment.core.service.talk.dto.SendMessageDTO;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/10:58 上午
 * @Description: 发送消息业务接口
 */
public interface SendMessageService {

    /**
     * 发送消息统一接口
     *
     * <p>
     *     1、注意：内容传参需要跟做下对接
     *     2、钉钉支持：文本、链接
     *     3、企业微信支持：文本、图片、文件、图文
     *     4、obj参数传值：
     *      文本：DingDingTextDTO、WeChatTextDTO
     *      图片：DingDingImageDTO、WeChatImageDTO
     *      链接：DingDingLinkDTO
     *      图文：WeChatNewsDTO 传List<WeChatNewsDTO>
     *      文件：WeChatFileDTO
     * </p>
     *
     * @param sendMessageDTO 外部传入内容
     */
    void sendMessage(SendMessageDTO sendMessageDTO);

}
