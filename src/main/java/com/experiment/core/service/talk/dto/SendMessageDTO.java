package com.experiment.core.service.talk.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/2:46 下午
 * @Description: 外部入参
 */

@Data
public class SendMessageDTO {

    /**
     * @see com.experiment.core.service.talk.enums.EnumSendPlatform
     */
    private Integer platFormCode;

    /**
     * @see com.experiment.core.service.talk.enums.EnumSendType
     */
    private Integer msgType;

    /**
     * 具体推送消息
     */
    private Object obj;

    /**
     * 机器人链接
     */
    private String robotUrl;

    /**
     * 钉钉独有 验签
     */
    private String sign;

}
