package com.experiment.core.service.talk.params;

import com.experiment.core.service.talk.enums.EnumSendType;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/01/26/1:28 下午
 * @Description: 钉钉文本消息
 */

@Data
public class DingDingTextDTO {

    /**
     * 发送类型
     * @see EnumSendType
     */
    private String msgtype;

    /**
     * 文本
     */
    private DingDingText text;

    @Data
    public static class DingDingText {

        /**
         * 内容
         */
        private String content;

    }

    /**
     * 提醒人
     */
    private DingDingAt at;

    @Data
    public static class DingDingAt {

        /**
         * 个人电话
         */
        private List<String> atMobiles;

        /**
         * 个人userId
         */
        private List<String> atUserIds;

        /**
         * 是否@全部人员
         */
        private Boolean isAtAll;

    }

}
