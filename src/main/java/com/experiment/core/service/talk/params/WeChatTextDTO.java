package com.experiment.core.service.talk.params;

import com.experiment.core.service.talk.enums.EnumSendType;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/25/3:47 下午
 * @Description: 企业微信文本
 */

@Data
public class WeChatTextDTO {

    /**
     * 发送类型
     * @see EnumSendType
     */
    private String msgtype;

    /**
     * 文本
     */
    private WeChatText text;

    @Data
    public static class WeChatText {

        /**
         * 文本描述
         */
        private String content;

        /**
         * 需要@的人员userId集合
         */
        private List<String> mentioned_list;

        /**
         * 需要@的人员电话号集合
         */
        private List<String> mentioned_mobile_list;

    }

}
